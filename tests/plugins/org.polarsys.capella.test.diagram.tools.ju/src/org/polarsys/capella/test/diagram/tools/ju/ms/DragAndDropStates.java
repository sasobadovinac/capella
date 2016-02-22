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
package org.polarsys.capella.test.diagram.tools.ju.ms;

import junit.framework.Test;

import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.polarsys.capella.core.data.capellacommon.Region;
import org.polarsys.capella.core.data.capellacommon.State;
import org.polarsys.capella.core.sirius.analysis.IDiagramNameConstants;
import org.polarsys.capella.core.sirius.analysis.constants.IToolNameConstants;
import org.polarsys.capella.test.diagram.common.ju.context.DiagramContext;
import org.polarsys.capella.test.diagram.common.ju.context.SessionContext;
import org.polarsys.capella.test.diagram.common.ju.step.crud.CreateDiagramStep;
import org.polarsys.capella.test.diagram.common.ju.step.crud.OpenDiagramStep;
import org.polarsys.capella.test.diagram.common.ju.step.tools.CreateAbstractDNodeTool;
import org.polarsys.capella.test.diagram.common.ju.step.tools.DragAndDropTool;
import org.polarsys.capella.test.diagram.tools.ju.model.EmptyProject;
import org.polarsys.capella.test.diagram.tools.ju.model.GenericModel;

public class DragAndDropStates extends EmptyProject {

  @Override
  public void test() throws Exception {
    Session session = getSession(getRequiredTestModel());
    SessionContext context = new SessionContext(session);

    DiagramContext diagramContext =
        new CreateDiagramStep(context, EmptyProject.SA__SYSTEM__SYSTEM_STATE_MACHINE__DEFAULT_REGION, IDiagramNameConstants.MODES_AND_STATES_DIAGRAM_NAME)
            .run();

    new OpenDiagramStep(diagramContext).run();

    new CreateAbstractDNodeTool<DDiagramElementContainer>(diagramContext, IToolNameConstants.TOOL_MS_CREATE_STATE, GenericModel.STATE_1, diagramContext.getDiagramId(), DDiagramElementContainer.class).run();
    new CreateAbstractDNodeTool<DDiagramElementContainer>(diagramContext, IToolNameConstants.TOOL_MS_CREATE_STATE, GenericModel.STATE_2, diagramContext.getDiagramId(), DDiagramElementContainer.class).run();

    new DragAndDropTool(diagramContext, "D&D ModeState from Diagram", GenericModel.STATE_1, GenericModel.STATE_2).run();

    Region region = context.getSemanticElement(EmptyProject.SA__SYSTEM__SYSTEM_STATE_MACHINE__DEFAULT_REGION);
    State state1 = context.getSemanticElement(GenericModel.STATE_1);
    State state2 = context.getSemanticElement(GenericModel.STATE_2);

    assertTrue(region.getOwnedStates().contains(state2));
    assertTrue("Default Region should not contain State1", !region.getOwnedStates().contains(state1));

    assertTrue(region.getInvolvedStates().contains(state2));
    assertTrue("Default Region should not involve State1", !region.getInvolvedStates().contains(state1));

    assertTrue(state2.getOwnedRegions().get(0).getInvolvedStates().contains(state1));
    assertTrue("State2 should involve State1", !region.getInvolvedStates().contains(state1));
  }

  public static Test suite() {
    return new DragAndDropStates();
  }
}
