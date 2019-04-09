package cz.mg.vulkantransformator;

import cz.mg.collections.text.Text;


public enum EntityGroup {
    C,
    VK;

    public Text getName(){
        return new Text(name());
    }
}