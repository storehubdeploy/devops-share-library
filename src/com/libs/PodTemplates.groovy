package com.libs

def Create(def containers) {
    def containerTemplates = []
    for (def i:containers ) {
        def container = containerTemplate(container.name ?: '', container.image ?: '')
        container.command           = i.command         ?: 'cat'
        container.args              = i.args            ?: ''
        container.ttyEnabled        = i.ttyEnabled      ?: true
        container.privileged        = i.privileged      ?: true
        container.alwaysPullImage   = i.alwaysPullImage ?: false
        // Add containerTemplate to list, and then return
        containerTemplates.add(container)

        // // Add containerTemplate to list, and then return
        // containerTemplates.add(containerTemplate(name: i.name, image: i.image, command: 'cat',ttyEnabled: true , privileged: true, alwaysPullImage: false))
    }
    return containerTemplates
}