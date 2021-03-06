/*******************************************************************************
 * Copyright (c) 2012-2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.m2e.wtp.tests.jsf.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.eclipse.m2e.wtp.jsf.internal.utils.FacesConfigQuickPeek;
import org.junit.Test;

public class FacesConfigQuickPeekTest extends TestCase {

	@Test
	public void testGetVersion() {
		assertVersion("2.0", getInputStream("<faces-config version=\"2.0\"></faces-config>"));
		assertVersion("1.2", getInputStream("<faces-config version=\"1.2\"></faces-config>"));
		assertVersion(null, getInputStream("<faces-config version=\"\"></faces-config>"));
		assertVersion(null, getInputStream("<faces-config></faces-config>"));
		assertVersion(null, getInputStream("<faces-config version=\"2.0\""));
		assertVersion(null, getInputStream(""));
		assertVersion(null, getInputStream("<dummy version=\"3.0\">"));
	}

	@Test
	public void testGetVersionFromDTD() {
		assertVersion("1.0", getInputStream("<!DOCTYPE faces-config PUBLIC \"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.0//EN\" \"http://java.sun.com/dtd/web-facesconfig_1_0.dtd\"><faces-config></faces-config>"));
		assertVersion("1.1", getInputStream("<!DOCTYPE faces-config PUBLIC \"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\" \"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\"><faces-config></faces-config>"));
		assertVersion("1.2", getInputStream("<faces-config xmlns=\"http://java.sun.com/xml/ns/javaee\""
										              +  " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
										              +  " xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facesconfig_1_2.xsd\" >"));
		assertVersion("2.0", getInputStream("<faces-config xmlns=\"http://java.sun.com/xml/ns/javaee\""
													  +  " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
													  +  " xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facesconfig_2_0.xsd\" >"));
		assertVersion("2.2", getInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
												"<faces-config xmlns=\"http://xmlns.jcp.org/xml/ns/javaee\"\r\n" + 
												"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
												"    xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/javaee\r\n" + 
												"        http://xmlns.jcp.org/xml/ns/javaee/web-facesconfig_2_2.xsd\"\r\n" + 
												"    version=\"2.2\">\r\n" + 
												"</faces-config>"));
	
	}

	private InputStream getInputStream(String s) {
		try {
			return (s == null)? null :new ByteArrayInputStream(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void assertVersion(String expectedVersion, InputStream in) {
		FacesConfigQuickPeek qp = new FacesConfigQuickPeek(in);
		assertEquals(expectedVersion, qp.getVersion());
	}
}
