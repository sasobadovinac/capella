/*******************************************************************************
 * Copyright (c) 2023 THALES GLOBAL SERVICES.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.core.sirius.ui.testers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.polarsys.capella.common.data.activity.ActivityNode;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.oa.OperationalActivity;
import org.polarsys.capella.core.sirius.analysis.FunctionalChainServices;

public class ValidFCMenuTargetTester extends PropertyTester {

  public ValidFCMenuTargetTester() {
  }

  @Override
  public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
    if (getPropertyId().equals(property)) {
      if(receiver instanceof IStructuredSelection) {
        IStructuredSelection selection = (IStructuredSelection) receiver;
        List<AbstractGraphicalEditPart> edgeEditParts = new ArrayList<AbstractGraphicalEditPart>();
        for (Object elem : selection.toList()) {
          if (elem instanceof AbstractGraphicalEditPart ) {
            edgeEditParts.add((AbstractGraphicalEditPart) elem);
          }
        }

        if (edgeEditParts.size() != selection.size())
          return false;

        List<EObject> diagramElements = edgeEditParts.stream().map(edgeEditPart -> edgeEditPart.getModel()).map(View.class::cast)
            .map(model -> model.getElement()).collect(Collectors.toList());

        EObject context = ((DDiagramElement) diagramElements.get(0)).getTarget();
        
        if (!isValidContext(context))
          return false;

        return FunctionalChainServices.getFunctionalChainServices().isValidFunctionalChainSelection(context,
            diagramElements);
      }
    }
    return false;
  }

  protected boolean isValidContext(EObject context) {
    if (context instanceof FunctionalExchange) {
      FunctionalExchange fe = (FunctionalExchange) context;
      ActivityNode sourceNode = fe.getSource();
      return !(sourceNode instanceof OperationalActivity);
    }
    return false;
  }

  protected String getPropertyId() {
    return "isValidFCMenuTarget";
  }
}
