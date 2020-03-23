/*******************************************************************************
 * Copyright (c) 2006, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.core.refinement.merge.merger;

import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.refinement.merge.exception.MergeException;

/**
 * Interface that should be implemented by the Scenario merger
 *
 */
public interface IScenarioMerger {
  
  /**
   * The suffix used in order to name scenarii that have been generated by the merge process.
   */
  public static final String MERGED_SCENARIO_SUFFIX = " [merged]"; //$NON-NLS-1$

  
  /**
   * Pre treatment operation for merge.
   * @param sc the top-level scenario to merge
   * @return <code>true</code> if no errors have been found, <code>false</code> otherwise 
   */
  public boolean preTreatment(final Scenario sc) throws MergeException;
  
  /**
   * the merge operation itself 
   * @param sc the top-level scenario to merge
   * @return the merged scenario if process reach its end, <code>null</code> otherwise
   */
  public Scenario doMerge(final Scenario sc) throws MergeException;
  
  /**
   * Post treatment operation for merge.
   * @param mergedScenarioResult the merged 
   * @return <code>true</code> if no errors have been found, <code>false</code> otherwise
   */
  public boolean postTreatment(Scenario mergedScenarioResult) throws MergeException;
  
}