package com.libs

def Create(containers) {
    def containerTemplates = []
    for (def container:containers ) {
        def containerTemplate = new ContainerTemplate()
        containerTemplate.name              = container.name            ?: ''
        containerTemplate.image             = container.image           ?: ''
        containerTemplate.command           = container.command         ?: 'cat'
        containerTemplate.args              = container.args            ?: ''
        containerTemplate.ttyEnabled        = container.ttyEnabled      ?: true
        containerTemplate.privileged        = container.privileged      ?: true
        containerTemplate.alwaysPullImage   = container.alwaysPullImage ?: false
        // Add containerTemplate to list, and then return
        containerTemplates.add(containerTemplate)
    }
    return containerTemplates
}