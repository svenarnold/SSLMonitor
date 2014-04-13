class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?(.$format)?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'monitor', action: 'list')
        "/status"(view: 'status')
		"500"(view:'/error')
	}
}
