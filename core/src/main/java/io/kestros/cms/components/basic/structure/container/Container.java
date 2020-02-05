package io.kestros.cms.components.basic.structure.container;

import io.kestros.cms.foundation.content.BaseComponent;
import io.kestros.commons.structuredslingmodels.annotation.StructuredModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@StructuredModel(validationService = ContainerValidationService.class)
@Model(adaptables = Resource.class, resourceType = "kestros/commons/components/structure/container")
public class Container extends BaseComponent {

}
