package com.libs

def Create(def containers) {
    def containerTemplates = []
    for (def i:containers ) {
        def containerTemplate = new containerTemplate(container.name ?: '', container.image ?: '')
        containerTemplate.name              = i.name            ?: ''
        containerTemplate.image             = i.image           ?: ''
        containerTemplate.command           = i.command         ?: 'cat'
        containerTemplate.args              = i.args            ?: ''
        containerTemplate.ttyEnabled        = i.ttyEnabled      ?: true
        containerTemplate.privileged        = i.privileged      ?: true
        containerTemplate.alwaysPullImage   = i.alwaysPullImage ?: false
        // Add containerTemplate to list, and then return
        containerTemplates.add(containerTemplate)

        // // Add containerTemplate to list, and then return
        // containerTemplates.add(containerTemplate(name: i.name, image: i.image, command: 'cat',ttyEnabled: true , privileged: true, alwaysPullImage: false))
    }
    return containerTemplates
}