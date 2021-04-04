package com.libs

def Create(def containers) {
    def containerTemplates = []
    for (def i:containers ) {
        // Add containerTemplate to list, and then return
        containerTemplates.add(containerTemplate(name: i.name, image: i.image, command: 'cat',ttyEnabled: true , privileged: true, alwaysPullImage: false))
    }
    return containerTemplates
}