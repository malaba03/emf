/**
 * Copyright (c) 2011-2012 Eclipse contributors and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.emf.ecore.xcore.generator;


import java.util.Collection;

import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.generator.IFileSystemAccess;

import com.google.inject.Inject;
import com.google.inject.Provider;

import static java.util.Collections.*;


public class XcoreGeneratorImpl extends Generator
{
  private IFileSystemAccess fsa;
  
  private String modelDirectory;

  public String getModelDirectory()
  {
    return modelDirectory;
  }

  public void setModelDirectory(String modelDirectory)
  {
    this.modelDirectory = modelDirectory;
  }

  public void setFileSystemAccess(IFileSystemAccess fsa)
  {
    this.fsa = fsa;
  }

  @Override
  public void setInput(Object input)
  {
    if (input instanceof GenModel)
    {
      GenModel genModel = (GenModel)input;
      setModelDirectory(genModel.getModelDirectory());
    }
    super.setInput(input);
  }

  @Inject
  private Provider<XcoreGenModelGeneratorAdapterFactory> adapterFactoryProvider;

  @Override
  protected Collection<GeneratorAdapterFactory> getAdapterFactories(Object object)
  {
    final XcoreGenModelGeneratorAdapterFactory genAdapterFactory = adapterFactoryProvider.get();
    genAdapterFactory.setGenerator(this);
    genAdapterFactory.setFileSystemAccess(fsa);
    genAdapterFactory.setModelDirectory(URI.createURI(modelDirectory));
    return singleton((GeneratorAdapterFactory)genAdapterFactory);
  }
}
