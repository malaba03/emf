/**
 * <copyright>
 *
 * Copyright (c) 2002-2004 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: AllSuites.java,v 1.11 2006/12/10 14:08:43 emerks Exp $
 */
package org.eclipse.emf.test.xml;


import org.eclipse.emf.test.xml.rss.RSSTests;
import org.eclipse.emf.test.xml.xmi.CrossResourceReferencesTest;
import org.eclipse.emf.test.xml.xsdecore.XSDEcoreBuilderTests;
import org.eclipse.emf.test.xml.xsdecore.XSDTests;

import junit.framework.Test;
import junit.framework.TestSuite;


public class AllSuites extends TestSuite
{
  private static Test[] suites = new Test []{ 
    org.eclipse.emf.test.xml.xmi.NamespaceTest.suite()
    ,org.eclipse.emf.test.xml.xmi.OrderTest.suite()
    ,org.eclipse.emf.test.xml.xmi.QNameTest.suite()
    ,CrossResourceReferencesTest.suite()
    ,org.eclipse.emf.test.xml.encoding.UnicodeEncodingTest.suite() 
    ,org.eclipse.emf.test.xml.xsdecore.Ecore2XSDTest.suite() 
    ,XSDEcoreBuilderTests.suite()
    ,org.eclipse.emf.test.xml.xsdecore.XSD2EcoreTest.suite()
    ,org.eclipse.emf.test.xml.xsdecore.XSDValidateTest.suite()
    ,XSDTests.suite()
    ,RSSTests.suite()
    ,ProcessingInstructionTest.suite()
    ,org.eclipse.emf.test.xml.xmi.URIHandlerTest.suite()
  };

  public static Test suite()
  {
    return new AllSuites("EMF XML and XMI JUnit Test Suite");
  }

  public AllSuites()
  {
    super();
    populateSuite();
  }

  public AllSuites(Class theClass)
  {
    super(theClass);
    populateSuite();
  }

  public AllSuites(String name)
  {
    super(name);
    populateSuite();
  }

  protected void populateSuite()
  {
    for (int i = 0; i < suites.length; i++)
    {
      addTest(suites[i]);
    }
  }
}