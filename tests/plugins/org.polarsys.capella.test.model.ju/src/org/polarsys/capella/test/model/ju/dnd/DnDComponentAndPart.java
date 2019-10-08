/*******************************************************************************
 * Copyright (c) 2019 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.test.model.ju.dnd;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.session.Session;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.la.LogicalComponentPkg;
import org.polarsys.capella.core.libraries.model.ICapellaModel;
import org.polarsys.capella.core.libraries.utils.ScopeModelWrapper;
import org.polarsys.capella.shared.id.handler.IScope;
import org.polarsys.capella.shared.id.handler.IdManager;
import org.polarsys.capella.test.framework.helpers.GuiActions;
import org.polarsys.capella.test.model.ju.model.MiscModel;

public class DnDComponentAndPart extends MiscModel {
  @Override
  public void test() throws Exception {
    ICapellaModel model = getTestModel();
    IScope scope = new ScopeModelWrapper(model);
    LogicalComponent lc1 = (LogicalComponent) IdManager.getInstance().getEObject(LC_1, scope);
    LogicalComponent lc2 = (LogicalComponent) IdManager.getInstance().getEObject(LC_2, scope);
    Part lc1Part = (Part) IdManager.getInstance().getEObject(LC_1_PART, scope);
    Part lc2Part = (Part) IdManager.getInstance().getEObject(LC_2_PART, scope);
    LogicalComponentPkg lcPkg1 = (LogicalComponentPkg) IdManager.getInstance().getEObject(LOGICALCOMPONENTPKG_1,
        scope);
    Session session = getSessionForTestModel(getRequiredTestModels().get(0));
    TransactionalEditingDomain ted = session.getTransactionalEditingDomain();

    // DnD LC1 to LCPkg1
    assertTrue(GuiActions.canDnD(lcPkg1, Arrays.asList(lc1)));
    GuiActions.dragAndDrop(ted, lcPkg1, Collections.singleton(lc1));
    assertTrue(lcPkg1.getOwnedLogicalComponents().size() == 1 && lcPkg1.getOwnedLogicalComponents().get(0) == lc1);
    assertTrue(lcPkg1.getOwnedParts().size() == 1 && lcPkg1.getOwnedParts().get(0) == lc1Part);
    
    // DnD LC2_Part to LC1
    assertTrue(GuiActions.canDnD(lc1, Arrays.asList(lc2Part)));
    GuiActions.dragAndDrop(ted, lc1, Collections.singleton(lc2Part));
    assertTrue(lc1.getOwnedLogicalComponents().size() == 1 && lc1.getOwnedLogicalComponents().get(0) == lc2);
    assertTrue(lc1.getContainedParts().size() == 1 && lc1.getContainedParts().get(0) == lc2Part);
    
  }
}