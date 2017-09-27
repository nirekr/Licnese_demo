task showLicenses {
    doFirst {
        configurations.compile.dependencies.each { Dependency dependency ->
            dependencies {
                poms(
                    group: dependency.group,
                     name: dependency.name,
                     version: dependency.version,
                     ext: 'pom'
                )
            }
        }
    }
          doLast {
        configurations.poms.each { File pom ->
            println "${pom.name}:"
            def project = new XmlSlurper().parse(pom)
            project.licenses.license.each {
                println it.name.text()+" - "+it.url.text()+"\n"
            }
        }
    }
}           


