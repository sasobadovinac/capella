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
package org.polarsys.capella.core.business.queries.pa;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.polarsys.capella.common.queries.interpretor.QueryInterpretor;
import org.polarsys.capella.common.queries.queryContext.QueryContext;
import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.business.queries.QueryConstants;
import org.polarsys.capella.core.data.pa.PaPackage;

/**
 *
 */
public class PhysicalActor_LogicalActorRealization implements IBusinessQuery {

	/**
	 * @see org.polarsys.capella.core.business.queries.core.business.queries.IBusinessQuery#getEClass()
	 */
	@Override
	public EClass getEClass() {
		return PaPackage.Literals.PHYSICAL_ACTOR;
	}

	/**
	 * @see org.polarsys.capella.core.business.queries.core.business.queries.IBusinessQuery#getEStructuralFeature()
	 */
	@Override
	public List<EReference> getEStructuralFeatures() {
		List<EReference> list = new ArrayList<EReference>(1);
		list.add(PaPackage.Literals.PHYSICAL_ACTOR__LOGICAL_ACTOR_REALIZATIONS);
		list.add(PaPackage.Literals.PHYSICAL_ACTOR__OWNED_LOGICAL_ACTOR_REALIZATIONS);
		return list;
	}

	@Override
	public List<EObject> getAvailableElements(EObject element) {
		QueryContext context = new QueryContext();
		context.putValue(QueryConstants.ECLASS_PARAMETER, getEClass());
		return QueryInterpretor.executeQuery(QueryConstants.GET_AVAILABLE__PHYSICAL_ACTOR__LOGICAL_ACTOR_REALIZATION, element, context);
	}

	@Override
	public List<EObject> getCurrentElements(EObject element, boolean onlyGenerated) {
		QueryContext context = new QueryContext();
		context.putValue(QueryConstants.ECLASS_PARAMETER, getEClass());
		return QueryInterpretor.executeQuery(QueryConstants.GET_CURRENT__PHYSICAL_ACTOR__LOGICAL_ACTOR_REALIZATION, element, context);
	}

}