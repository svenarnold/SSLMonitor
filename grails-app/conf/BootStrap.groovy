class BootStrap {

    def init = { servletContext ->

        String.metaClass.truncate = { maxLength ->
            delegate ? (delegate.length() > maxLength ? delegate[0..maxLength-2] + '\u2026' : delegate ): ''
        }

    }
    def destroy = {
    }
}
