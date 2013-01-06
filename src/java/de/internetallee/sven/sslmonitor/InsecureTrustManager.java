/*
 * This file is part of SSLMonitor
 *
 * Copyright (c) 2013 Sven Arnold
 *
 * SSLMonitor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SSLMonitor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SSLMonitor. If not, see <http://www.gnu.org/licenses/>.
 */

package de.internetallee.sven.sslmonitor;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * This implementation of an X.509 Trust Manager does not check any certificate at all.
 *
 * It can be used to retrieve certificate information of an SSL endpoint but must not be used to establish
 * a secured connection.
 */
public class InsecureTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
