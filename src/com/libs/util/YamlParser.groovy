package com.libs.util

@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

public class YamlParser implements Serializable {

    private Yaml yaml;
    private def yamlObj;

    public YamlParser(String yamlText) {
        yaml = new Yaml()
        yamlObj = yaml.load(yamlText)
    }

    public static def loadYaml(def yamlPath = "build.yaml") {
        def parser = new YamlParser(yamlText)
        return parser.yamlObj
    }


}

// public class YamlParser implements Serializable {

//     private Yaml yaml;
//     private def yamlObj;



//     public YamlParser(String yamlText) {
//         yaml = new Yaml()
//         yamlObj = yaml.load(yamlText)
//     }

//     public YamlParser() {
//         yaml = new Yaml()
//     }

//     private def dumpYamlObj(def yamlObj) {
//         return yaml.dump(yamlObj)
//     }

//     private getYamlValue(def yamlKeyPath) {
//         def localYamlObj = yamlObj
//         for (def keyPath : yamlKeyPath.split("/")) {
//             localYamlObj = localYamlObj[keyPath]
//             if (!localYamlObj)
//                 break
//         }
//         return localYamlObj
//     }

//     public static def loadYaml(def yamlText, def yamlKeyPath = null) {
//         def parser = new YamlParser(yamlText)
//         if (yamlKeyPath) {
//             return parser.getYamlValue(yamlKeyPath)
//         } else {
//             return parser.yamlObj
//         }
//     }

//     public static def dumpYaml(def yamlObj) {
//         def parser = new YamlParser()
//         return parser.dumpYamlObj(yamlObj)
//     }

// }
