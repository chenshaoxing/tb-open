/*
 * Copyright (c) 2010, 2014, Jumei and/or its affiliates. All rights reserved.
 * JUMEI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.taobao.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供配置参数访问的途径
 *
 * Created by 黄刚 on 2014/8/10.
 */
public class Configuration
        implements Iterable<Map.Entry<String, String>>
{
    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);
    private boolean quietmode = true;
    private ArrayList<Object> resources = new ArrayList();
    private Set<String> finalParameters = new HashSet();
    private boolean loadDefaults = true;
    private static final WeakHashMap<Configuration, Object> REGISTRY = new WeakHashMap();
    private static final CopyOnWriteArrayList<String> defaultResources = new CopyOnWriteArrayList();
    private boolean storeResource;
    private HashMap<String, String> updatingResource;
    private Properties properties;
    private Properties overlay;

    static
    {
        ClassLoader cL = Thread.currentThread().getContextClassLoader();
        if (cL == null) {
            cL = Configuration.class.getClassLoader();
        }
        if (cL.getResource("hadoop-site.xml") != null) {
            LOG.warn("DEPRECATED: hadoop-site.xml found in the classpath. Usage of hadoop-site.xml is deprecated. Instead use core-site.xml, mapred-site.xml and hdfs-site.xml to override properties of core-default.xml, mapred-default.xml and hdfs-default.xml respectively");
        }
        addDefaultResource("core-default.xml");
        addDefaultResource("core-site.xml");
    }

    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public Configuration()
    {
        this(true);
    }

    public Configuration(boolean loadDefaults)
    {
        if (this.classLoader == null) {
            this.classLoader = Configuration.class.getClassLoader();
        }
        this.loadDefaults = loadDefaults;
//        if (LOG.isDebugEnabled()) {
//            LOG.debug(StringUtils.stringifyException(new IOException("config()")));
//        }
        synchronized (Configuration.class)
        {
            REGISTRY.put(this, null);
        }
        this.storeResource = false;
    }

    private Configuration(Configuration other, boolean storeResource)
    {
        this(other);
        this.loadDefaults = other.loadDefaults;
        this.storeResource = storeResource;
        if (storeResource) {
            this.updatingResource = new HashMap();
        }
    }

    public Configuration(Configuration other)
    {
        if (this.classLoader == null) {
            this.classLoader = Configuration.class.getClassLoader();
        }
//        if (LOG.isDebugEnabled()) {
//            LOG.debug(StringUtils.stringifyException(new IOException("config(config)")));
//        }
        this.resources = ((ArrayList)other.resources.clone());
        synchronized (other)
        {
            if (other.properties != null) {
                this.properties = ((Properties)other.properties.clone());
            }
            if (other.overlay != null) {
                this.overlay = ((Properties)other.overlay.clone());
            }
        }
        this.finalParameters = new HashSet(other.finalParameters);
        synchronized (Configuration.class)
        {
            REGISTRY.put(this, null);
        }
    }

    public static synchronized void addDefaultResource(String name)
    {
        if (!defaultResources.contains(name))
        {
            defaultResources.add(name);
            for (Configuration conf : REGISTRY.keySet()) {
                if (conf.loadDefaults) {
                    conf.reloadConfiguration();
                }
            }
        }
    }

    public void addResource(String name)
    {
        addResourceObject(name);
    }

    public void addResource(URL url)
    {
        addResourceObject(url);
    }

    public void addResource(InputStream in)
    {
        addResourceObject(in);
    }

    public synchronized void reloadConfiguration()
    {
        this.properties = null;
        this.finalParameters.clear();
    }

    private synchronized void addResourceObject(Object resource)
    {
        this.resources.add(resource);
        reloadConfiguration();
    }

    private static Pattern varPat = Pattern.compile("\\$\\{[^\\}\\$ ]+\\}");
    private static int MAX_SUBST = 20;

    private String substituteVars(String expr)
    {
        if (expr == null) {
            return null;
        }
        Matcher match = varPat.matcher("");
        String eval = expr;
        for (int s = 0; s < MAX_SUBST; s++)
        {
            match.reset(eval);
            if (!match.find()) {
                return eval;
            }
            String var = match.group();
            var = var.substring(2, var.length() - 1);
            String val = null;
            try
            {
                val = System.getProperty(var);
            }
            catch (SecurityException se)
            {
                LOG.warn("Unexpected SecurityException in Configuration", se);
            }
            if (val == null) {
                val = getRaw(var);
            }
            if (val == null) {
                return eval;
            }
            eval = eval.substring(0, match.start()) + val + eval.substring(match.end());
        }
        throw new IllegalStateException("Variable substitution depth too large: " + MAX_SUBST + " " + expr);
    }

    public String get(String name)
    {
        return substituteVars(getProps().getProperty(name));
    }

    public String getRaw(String name)
    {
        return getProps().getProperty(name);
    }

    public void set(String name, String value)
    {
        getOverlay().setProperty(name, value);
        getProps().setProperty(name, value);
    }

    public void setIfUnset(String name, String value)
    {
        if (get(name) == null) {
            set(name, value);
        }
    }

    private synchronized Properties getOverlay()
    {
        if (this.overlay == null) {
            this.overlay = new Properties();
        }
        return this.overlay;
    }

    public String get(String name, String defaultValue)
    {
        return substituteVars(getProps().getProperty(name, defaultValue));
    }

    public int getInt(String name, int defaultValue)
    {
        String valueString = get(name);
        if (valueString == null) {
            return defaultValue;
        }
        try
        {
            String hexString = getHexDigits(valueString);
            if (hexString != null) {
                return Integer.parseInt(hexString, 16);
            }
            return Integer.parseInt(valueString);
        }
        catch (NumberFormatException e) {}
        return defaultValue;
    }

    public void setInt(String name, int value)
    {
        set(name, Integer.toString(value));
    }

    public long getLong(String name, long defaultValue)
    {
        String valueString = get(name);
        if (valueString == null) {
            return defaultValue;
        }
        try
        {
            String hexString = getHexDigits(valueString);
            if (hexString != null) {
                return Long.parseLong(hexString, 16);
            }
            return Long.parseLong(valueString);
        }
        catch (NumberFormatException e) {}
        return defaultValue;
    }

    private String getHexDigits(String value)
    {
        boolean negative = false;
        String str = value;
        String hexString = null;
        if (value.startsWith("-"))
        {
            negative = true;
            str = value.substring(1);
        }
        if ((str.startsWith("0x")) || (str.startsWith("0X")))
        {
            hexString = str.substring(2);
            if (negative) {
                hexString = "-" + hexString;
            }
            return hexString;
        }
        return null;
    }

    public void setLong(String name, long value)
    {
        set(name, Long.toString(value));
    }

    public float getFloat(String name, float defaultValue)
    {
        String valueString = get(name);
        if (valueString == null) {
            return defaultValue;
        }
        try
        {
            return Float.parseFloat(valueString);
        }
        catch (NumberFormatException e) {}
        return defaultValue;
    }

    public void setFloat(String name, float value)
    {
        set(name, Float.toString(value));
    }

    public boolean getBoolean(String name, boolean defaultValue)
    {
        String valueString = get(name);
        if ("true".equals(valueString)) {
            return true;
        }
        if ("false".equals(valueString)) {
            return false;
        }
        return defaultValue;
    }

    public void setBoolean(String name, boolean value)
    {
        set(name, Boolean.toString(value));
    }

    public void setBooleanIfUnset(String name, boolean value)
    {
        setIfUnset(name, Boolean.toString(value));
    }

    public <T extends Enum<T>> void setEnum(String name, T value)
    {
        set(name, value.toString());
    }

    public <T extends Enum<T>> T getEnum(String name, T defaultValue)
    {
        String val = get(name);
        return null == val ? defaultValue : Enum.valueOf(defaultValue.getDeclaringClass(), val);
    }

    public static class IntegerRanges
    {
        List<Range> ranges = new ArrayList();

        public IntegerRanges() {}

        public IntegerRanges(String newValue)
        {
            StringTokenizer itr = new StringTokenizer(newValue, ",");
            while (itr.hasMoreTokens())
            {
                String rng = itr.nextToken().trim();
                String[] parts = rng.split("-", 3);
                if ((parts.length < 1) || (parts.length > 2)) {
                    throw new IllegalArgumentException("integer range badly formed: " + rng);
                }
                Range r = new Range();
                r.start = convertToInt(parts[0], 0);
                if (parts.length == 2) {
                    r.end = convertToInt(parts[1], 2147483647);
                } else {
                    r.end = r.start;
                }
                if (r.start > r.end) {
                    throw new IllegalArgumentException("IntegerRange from " + r.start + " to " + r.end + " is invalid");
                }
                this.ranges.add(r);
            }
        }

        private static int convertToInt(String value, int defaultValue)
        {
            String trim = value.trim();
            if (trim.length() == 0) {
                return defaultValue;
            }
            return Integer.parseInt(trim);
        }

        public boolean isIncluded(int value)
        {
            for (Range r : this.ranges) {
                if ((r.start <= value) && (value <= r.end)) {
                    return true;
                }
            }
            return false;
        }

        public String toString()
        {
            StringBuffer result = new StringBuffer();
            boolean first = true;
            for (Range r : this.ranges)
            {
                if (first) {
                    first = false;
                } else {
                    result.append(',');
                }
                result.append(r.start);
                result.append('-');
                result.append(r.end);
            }
            return result.toString();
        }

        private static class Range
        {
            int start;
            int end;
        }
    }

    public IntegerRanges getRange(String name, String defaultValue)
    {
        return new IntegerRanges(get(name, defaultValue));
    }

    public Collection<String> getStringCollection(String name)
    {
        String valueString = get(name);
        return StringUtils.getStringCollection(valueString);
    }

    public String[] getStrings(String name)
    {
        String valueString = get(name);
        return StringUtils.getStrings(valueString);
    }

    public String[] getStrings(String name, String... defaultValue)
    {
        String valueString = get(name);
        if (valueString == null) {
            return defaultValue;
        }
        return StringUtils.getStrings(valueString);
    }

    public void setStrings(String name, String... values)
    {
        set(name, StringUtils.arrayToString(values));
    }

    public Class<?> getClassByName(String name)
            throws ClassNotFoundException
    {
        return Class.forName(name, true, this.classLoader);
    }

    public Class<?>[] getClasses(String name, Class<?>... defaultValue)
    {
        String[] classnames = getStrings(name);
        if (classnames == null) {
            return defaultValue;
        }
        try
        {
            Class<?>[] classes = new Class[classnames.length];
            for (int i = 0; i < classnames.length; i++) {
                classes[i] = getClassByName(classnames[i]);
            }
            return classes;
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getClass(String name, Class<?> defaultValue)
    {
        String valueString = get(name);
        if (valueString == null) {
            return defaultValue;
        }
        try
        {
            return getClassByName(valueString);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public <U> Class<? extends U> getClass(String name, Class<? extends U> defaultValue, Class<U> xface)
    {
        try
        {
            Class<?> theClass = getClass(name, defaultValue);
            if ((theClass != null) && (!xface.isAssignableFrom(theClass))) {
                throw new RuntimeException(theClass + " not " + xface.getName());
            }
            if (theClass != null) {
                return theClass.asSubclass(xface);
            }
            return null;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setClass(String name, Class<?> theClass, Class<?> xface)
    {
        if (!xface.isAssignableFrom(theClass)) {
            throw new RuntimeException(theClass + " not " + xface.getName());
        }
        set(name, theClass.getName());
    }

    public File getFile(String dirsProp, String path)
            throws IOException
    {
        String[] dirs = getStrings(dirsProp);
        int hashCode = path.hashCode();
        for (int i = 0; i < dirs.length; i++)
        {
            int index = (hashCode + i & 0x7FFFFFFF) % dirs.length;
            File file = new File(dirs[index], path);
            File dir = file.getParentFile();
            if ((dir.exists()) || (dir.mkdirs())) {
                return file;
            }
        }
        throw new IOException("No valid local directories in property: " + dirsProp);
    }

    public URL getResource(String name)
    {
        return this.classLoader.getResource(name);
    }

    public InputStream getConfResourceAsInputStream(String name)
    {
        try
        {
            URL url = getResource(name);
            if (url == null)
            {
                LOG.info(name + " not found");
                return null;
            }
            LOG.info("found resource " + name + " at " + url);


            return url.openStream();
        }
        catch (Exception e) {}
        return null;
    }

    public Reader getConfResourceAsReader(String name)
    {
        try
        {
            URL url = getResource(name);
            if (url == null)
            {
                LOG.info(name + " not found");
                return null;
            }
            LOG.info("found resource " + name + " at " + url);


            return new InputStreamReader(url.openStream());
        }
        catch (Exception e) {}
        return null;
    }

    private synchronized Properties getProps()
    {
        if (this.properties == null)
        {
            this.properties = new Properties();
            loadResources(this.properties, this.resources, this.quietmode);
            if (this.overlay != null)
            {
                this.properties.putAll(this.overlay);
                if (this.storeResource) {
                    for (Map.Entry<Object, Object> item : this.overlay.entrySet()) {
                        this.updatingResource.put((String)item.getKey(), "Unknown");
                    }
                }
            }
        }
        return this.properties;
    }

    public int size()
    {
        return getProps().size();
    }

    public void clear()
    {
        getProps().clear();
        getOverlay().clear();
    }

    public Iterator<Map.Entry<String, String>> iterator()
    {
        Map<String, String> result = new HashMap();
        for (Map.Entry<Object, Object> item : getProps().entrySet()) {
            if (((item.getKey() instanceof String)) && ((item.getValue() instanceof String))) {
                result.put((String)item.getKey(), (String)item.getValue());
            }
        }
        return result.entrySet().iterator();
    }

    private void loadResources(Properties properties, ArrayList resources, boolean quiet)
    {
        if (this.loadDefaults)
        {
            for (String resource : defaultResources) {
                loadResource(properties, resource, quiet);
            }
            if (getResource("hadoop-site.xml") != null) {
                loadResource(properties, "hadoop-site.xml", quiet);
            }
        }
        for (Object resource : resources) {
            loadResource(properties, resource, quiet);
        }
    }

    private void loadResource(Properties properties, Object name, boolean quiet)
    {
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();


            docBuilderFactory.setIgnoringComments(true);


            docBuilderFactory.setNamespaceAware(true);
            try
            {
                docBuilderFactory.setXIncludeAware(true);
            }
            catch (UnsupportedOperationException e)
            {
                LOG.error("Failed to set setXIncludeAware(true) for parser " + docBuilderFactory + ":" + e, e);
            }
            DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
            Document doc = null;
            Element root = null;
            if ((name instanceof URL))
            {
                URL url = (URL)name;
                if (url != null)
                {
                    if (!quiet) {
                        LOG.info("parsing " + url);
                    }
                    doc = builder.parse(url.toString());
                }
            }
            else if ((name instanceof String))
            {
                URL url = getResource((String)name);
                if (url != null)
                {
                    if (!quiet) {
                        LOG.info("parsing " + url);
                    }
                    doc = builder.parse(url.toString());
                }
            }
            else if ((name instanceof InputStream))
            {
                try
                {
                    doc = builder.parse((InputStream)name);
                }
                finally
                {
                    ((InputStream)name).close();
                }
            }
            else if ((name instanceof Element))
            {
                root = (Element)name;
            }
            if ((doc == null) && (root == null))
            {
                if (quiet) {
                    return;
                }
                throw new RuntimeException(name + " not found");
            }
            if (root == null) {
                root = doc.getDocumentElement();
            }
            if (!"configuration".equals(root.getTagName())) {
                LOG.error("bad conf file: top-level element not <configuration>");
            }
            NodeList props = root.getChildNodes();
            for (int i = 0; i < props.getLength(); i++)
            {
                Node propNode = props.item(i);
                if ((propNode instanceof Element))
                {
                    Element prop = (Element)propNode;
                    if ("configuration".equals(prop.getTagName()))
                    {
                        loadResource(properties, prop, quiet);
                    }
                    else
                    {
                        if (!"property".equals(prop.getTagName())) {
                            LOG.warn("bad conf file: element not <property>");
                        }
                        NodeList fields = prop.getChildNodes();
                        String attr = null;
                        String value = null;
                        boolean finalParameter = false;
                        for (int j = 0; j < fields.getLength(); j++)
                        {
                            Node fieldNode = fields.item(j);
                            if ((fieldNode instanceof Element))
                            {
                                Element field = (Element)fieldNode;
                                if (("name".equals(field.getTagName())) && (field.hasChildNodes())) {
                                    attr = ((Text)field.getFirstChild()).getData().trim();
                                }
                                if (("value".equals(field.getTagName())) && (field.hasChildNodes())) {
                                    value = ((Text)field.getFirstChild()).getData();
                                }
                                if (("final".equals(field.getTagName())) && (field.hasChildNodes())) {
                                    finalParameter = "true".equals(((Text)field.getFirstChild()).getData());
                                }
                            }
                        }
                        if (attr != null)
                        {
                            if (value != null) {
                                if (!this.finalParameters.contains(attr))
                                {
                                    properties.setProperty(attr, value);
                                    if (this.storeResource) {
                                        this.updatingResource.put(attr, name.toString());
                                    }
                                }
                                else if (!value.equals(properties.getProperty(attr)))
                                {
                                    LOG.warn(name + ":a attempt to override final parameter: " + attr + ";  Ignoring.");
                                }
                            }
                            if (finalParameter) {
                                this.finalParameters.add(attr);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            LOG.error("error parsing conf file: " + e);
            throw new RuntimeException(e);
        }
        catch (DOMException e)
        {
            LOG.error("error parsing conf file: " + e);
            throw new RuntimeException(e);
        }
        catch (SAXException e)
        {
            LOG.error("error parsing conf file: " + e);
            throw new RuntimeException(e);
        }
        catch (ParserConfigurationException e)
        {
            LOG.error("error parsing conf file: " + e);
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> listAllConfEntry() {
        Map<String, String> map = new HashMap();
        Properties properties = getProps();
        for (Enumeration e = properties.keys(); e.hasMoreElements();) {
            String key = (String)e.nextElement();
            Object object = properties.get(key);
            String value = null;
            if ((object instanceof String)) {
                value = (String)object;
                map.put(key, value);
            }
        }
        return map;
    }

    public void writeXml(OutputStream out)
            throws IOException
    {
        Properties properties = getProps();
        try
        {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element conf = doc.createElement("configuration");
            doc.appendChild(conf);
            conf.appendChild(doc.createTextNode("\n"));
            for (Enumeration e = properties.keys(); e.hasMoreElements();)
            {
                String name = (String)e.nextElement();
                Object object = properties.get(name);
                String value = null;
                if ((object instanceof String))
                {
                    value = (String)object;



                    Element propNode = doc.createElement("property");
                    conf.appendChild(propNode);

                    Element nameNode = doc.createElement("name");
                    nameNode.appendChild(doc.createTextNode(name));
                    propNode.appendChild(nameNode);

                    Element valueNode = doc.createElement("value");
                    valueNode.appendChild(doc.createTextNode(value));
                    propNode.appendChild(valueNode);

                    conf.appendChild(doc.createTextNode("\n"));
                }
            }
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(out);
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public ClassLoader getClassLoader()
    {
        return this.classLoader;
    }

    public void setClassLoader(ClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Configuration: ");
        if (this.loadDefaults)
        {
            toString(defaultResources, sb);
            if (this.resources.size() > 0) {
                sb.append(", ");
            }
        }
        toString(this.resources, sb);
        return sb.toString();
    }

    private void toString(List resources, StringBuffer sb)
    {
        ListIterator i = resources.listIterator();
        while (i.hasNext())
        {
            if (i.nextIndex() != 0) {
                sb.append(", ");
            }
            sb.append(i.next());
        }
    }

    public synchronized void setQuietMode(boolean quietmode)
    {
        this.quietmode = quietmode;
    }

    public static void main(String[] args)
            throws Exception
    {
        new Configuration().writeXml(System.out);
    }

    public Map<String, String> getValByRegex(String regex)
    {
        Pattern p = Pattern.compile(regex);

        Map<String, String> result = new HashMap();
        for (Map.Entry<Object, Object> item : getProps().entrySet()) {
            if (((item.getKey() instanceof String)) && ((item.getValue() instanceof String)))
            {
                Matcher m = p.matcher((String)item.getKey());
                if (m.find()) {
                    result.put((String)item.getKey(), (String)item.getValue());
                }
            }
        }
        return result;
    }
}