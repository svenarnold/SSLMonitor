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

    logout(controller: 'logout', action: 'index')

    status(view: 'status')

	monitoredServer visible: false
    }
}
