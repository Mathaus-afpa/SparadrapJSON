package sparadrap.dao;

import com.sun.tools.javac.util.Pair;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class UniversalJSON
{
    private boolean useChecksum = true;
    private Map<String, Object> values = new HashMap<>();
    private Map<String, DataTypes> datatypes = new HashMap<>();
    private Map<String, Pair<String, String>> checksums = new HashMap<>();

    private void addChecksum(String key, String strValue) {
        if (this.useChecksum) {
            String md5Checksum = org.apache.commons.codec.digest.DigestUtils.md5Hex(strValue);
            String sha1Checksum = org.apache.commons.codec.digest.DigestUtils.sha1Hex(strValue);
            Pair<String, String> tuple = Pair.of(md5Checksum, sha1Checksum);
            this.checksums.put(key, tuple);
        }
    }

    private String dateFormat(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    private boolean ifNewKey(String key) {
        return !this.values.containsKey(key) && !this.datatypes.containsKey(key) && !this.checksums.containsKey(key);
    }
    public void putStr(String key, String value) {
        if (ifNewKey(key)) {
            this.values.put(key, value);
            this.datatypes.put(key, DataTypes.VARCHAR);
            this.addChecksum(key, value);
        }
    }
    public void putInt(String key, Integer value) {
        if (this.useChecksum) {
            this.values.put(key, value);
            this.datatypes.put(key, DataTypes.INTEGER);
            this.addChecksum(key, Integer.toString(value));
        }
    }
    public void putDouble(String key, Double value) {
        if (this.useChecksum) {
            this.values.put(key, value);
            this.datatypes.put(key, DataTypes.DOUBLE);
            this.addChecksum(key, Double.toString(value));
        }
    }
    public void putFloat(String key, Float value) {
        if (this.useChecksum) {
            this.values.put(key, value);
            this.datatypes.put(key, DataTypes.FLOAT);
            this.addChecksum(key, Float.toString(value));
        }
    }
    public void putDate(String key, Date value) {
        if (this.useChecksum) {
            this.values.put(key, value);
            this.datatypes.put(key, DataTypes.DATE);
            this.addChecksum(key, dateFormat(value));
        }
    }
    public void putDateTime(String key, Timestamp value) {
        if (this.useChecksum) {
            this.values.put(key, value);
            this.datatypes.put(key, DataTypes.TIMESTAMP);
            this.addChecksum(key, dateFormat(value));
        }
    }
    public void putBoolean(String key, Boolean value) {
        if (this.useChecksum) {
            this.values.put(key, value);
            this.datatypes.put(key, DataTypes.BOOLEAN);
            this.addChecksum(key, Boolean.toString(value));
        }
    }
    public void putBlob(String key, byte[] value) {
        if (this.useChecksum) {
            this.values.put(key, value);
            this.datatypes.put(key, DataTypes.BLOB);
            this.addChecksum(key, Arrays.toString(value));
        }
    }

    public Object get(String key) {
        return values.get(key);
    }

    public Map<String, Object> getValues() {
        return values;
    }
}