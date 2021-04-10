package com.libs.util

import hudson.model.*;
import groovy.json.*;

def loadJson(file_or_string) {
    def data

    if(fileExists(file_or_string.toString())){
        data = readJSON file : file_or_string
    }else {
        data = readJSON text : file_or_string
    }
    data.each {
        println ( it.key + " = " + it.value )
    }
}

def saveJson(file_or_string, tofile_path) {
    def data

    if(fileExists(file_or_string.toString())) {
        data = readJSON file : file_or_string
    }else {
        def jsonSlurper = new JsonSlurper(file_or_string)
        def new_json_object = jsonSlurper.parseText
        data = new_json_object
    }
    writeJSON file: tofile_path, json: data
}

def loadProperties(properties_file) {
     def data = readProperties interpolate: true, file: properties_file
     data.each {
        println ( it.key + " = " + it.value )
     }
}

def loadYaml(file_or_string) {
    def data

    if(file_or_string.toString().endsWith(".yml")){
        data = readYaml file : file_or_string
    }else {
        data = readYaml text : file_or_string
    }
    data.each {
        println ( it.key + " = " + it.value )
    }
}

def saveYaml(data, yaml_path) {
    writeYaml file: yaml_path , data: data
}

return this;