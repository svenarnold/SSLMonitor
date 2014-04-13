navigation = {

    app {
     
        monitor(controller: 'monitor', action: 'list') {
            list()
            create()
            show(visible: false)
        }

        x509CertificateInformation(action: 'list') {
            list()
            updateChains()
        }

        status(view: 'status')

        logout(controller: 'logout', action: 'index')

        monitoredServer visible: false
    }
}
