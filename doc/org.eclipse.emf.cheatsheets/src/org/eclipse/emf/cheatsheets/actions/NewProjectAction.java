/**
 * <copyright>
 *
 * Copyright (c) 2006 IBM Corporation and others.
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
 * $Id: NewProjectAction.java,v 1.3 2007/06/12 20:56:18 emerks Exp $
 */
package org.eclipse.emf.cheatsheets.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import org.eclipse.emf.cheatsheets.CheatSheetsPlugin;


/**
 * <p>Action that creates a new general project in workspace.</p>
 * @since 2.2.0
 */
public class NewProjectAction extends Action implements ICheatSheetAction
{
  protected String projectName;

  /**
   * Execution of the action
   * @param params Array of parameters - index 0: projectName
   * @param manager Cheatsheet Manager
   */
  public void run(String[] params, ICheatSheetManager manager)
  {
    projectName = params[0];
    run();
  }

  /**
   * Run the action
   */
  @Override
  public void run()
  {
    if (projectName != null)
    {
      WorkspaceModifyOperation operation = new WorkspaceModifyOperation()
        {
          @Override
          protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException
          {
            createProject(projectName, monitor);
          }
        };

      try
      {
        runWithProgress(operation);
        notifyResult(ResourcesPlugin.getWorkspace().getRoot().exists(new Path(projectName)));
        return;
      }
      catch (Exception e)
      {
        CheatSheetsPlugin.INSTANCE.log(e);
      }
    }

    notifyResult(false);
  }

  /**
   * Run with progress
   * @param runnable
   * @throws InvocationTargetException
   * @throws InterruptedException
   */
  protected void runWithProgress(IRunnableWithProgress runnable) throws InvocationTargetException, InterruptedException
  {
    new ProgressMonitorDialog(Display.getCurrent().getActiveShell()).run(false, false, runnable);
  }

  /**
   * Creates a new project
   * @param projectName Name of the project
   * @return Project
   * @throws CoreException 
   * @throws CoreException
   */
  protected IProject createProject(String projectName, IProgressMonitor monitor) throws CoreException
  {
    monitor.beginTask(CheatSheetsPlugin.INSTANCE.getString("_UI_CreateProject_message", new String []{ projectName }), 2);
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IProject project = workspaceRoot.getProject(projectName);
    if (!project.exists())
    {
      project.create(new SubProgressMonitor(monitor, 1));
    }
    project.open(new SubProgressMonitor(monitor, 1));
    monitor.done();
    return project;
  }
}
