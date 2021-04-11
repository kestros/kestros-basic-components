package io.kestros.cms.components.basic.content.filedownload;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/file-download")
public class FileDownloadComponent extends BaseComponent {

}
