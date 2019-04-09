package cz.mg.vulkantransformator;

import cz.mg.collections.text.Text;


public enum EntityType {
    SYSTEM_TYPE,
    TYPE,
    ENUM,
    FLAGS,
    FLAG_BITS,
    HANDLE,
    STRUCTURE,
    UNION,
    INFO,
    CALLBACK,
    FUNCTION,
    EXTENSION,
    DEFINE,
    MISC,
    VALUE,
    VARIABLE;

    public Text getName(){
        return new Text(name());
    }
}