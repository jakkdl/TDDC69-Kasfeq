package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources;

public interface ResourceLoader<LoadedType> {
    public LoadedType loadResource(String filename) throws Exception;
}
