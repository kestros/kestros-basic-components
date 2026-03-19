# Kestros Basic Components

Reusable Sling components for the Kestros CMS component library.

## Component List

### Content Components

| Component | Datasources | Notes |
|---|---|---|
| Alert | `default` (static) | |
| Button | `default` (static) | |
| Button Group | `default` (static) | Container |
| Card | `default` (static), `asset-card`, `page-card` | |
| Code | `default` (static) | |
| Heading | `default` (static), `page-title` | |
| Image | `default` (static), `asset-image` | |
| Link | `default` (static) | |
| Rich Text | `default` (static) | |
| Table | `default` (static) | Container |
| Table Row | `default` (static) | Container |
| Table Cell | `default` (static) | |
| Table Header | `default` (static) | |
| Text | `default` (static), `richtext` | |
| Video | `default` (static), `asset-video` | |
| Video Embed | `default` (YouTube) | |

### List Components

| Component | Datasources | Notes |
|---|---|---|
| Card List | `default` (static), `child-pages`, `asset-list`, `tag-search` | Container |
| Link List | `default` (static), `child-pages`, `tag-search` | Container |

### Navigation Components

| Component | Datasources | Notes |
|---|---|---|
| Breadcrumb | `default` (static) | |
| Breadcrumbs | `default` (page-path) | Container |
| Navigation | `default` (static) | Container |
| Navigation Item | `default` (static) | |
| Top Navigation | `default` (static) | Container |
| Top Navigation Item | `default` (static) | |

### Structure Components

| Component | Datasources | Notes |
|---|---|---|
| Accordion | `default` (static) | Container |
| Accordion Panel | `default` (static) | Container |
| Container | `default` (static) | Container |
| Grid | `default` (static) | Container |
| Section | `default` (static) | Container |

## Datasource Architecture

Each component has a `datasources/` directory containing JSON files that register available datasources. The filename (minus `.json`) becomes the datasource name. Content instances select a datasource via the `kes:datasource` property; omitting it defaults to `default`.

### Datasource Types

- **Static** (`default.json`) - Reads from JCR properties on the content resource.
- **Asset** (`asset-*.json`) - Resolves data from an asset reference.
- **Page** (`page-*.json`, `child-pages.json`) - Generates entries from page tree traversal.
- **Tag Search** (`tag-search.json`) - Filters sibling pages by tag match using `kestros-tagging-api`.

### Java Class Hierarchy

- `BaseSlingModelDataSource` - Base for simple (non-container) datasources
- `BaseContainerSlingModelDataSource` - Base for container datasources with typed children
- `BaseDataSourceComponent` - HTL-facing adapter that delegates to the resolved datasource
- `BaseContainerDataSourceComponent` - HTL-facing adapter for container components

## Custom Data Sources

### Adding a New Datasource

1. Create a Java class extending `BaseSlingModelDataSource` (or `BaseContainerSlingModelDataSource` for containers) that implements the component interface
2. Add `@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})` annotation
3. Implement the interface methods
4. Create a JSON file in the component's `datasources/` directory with `classPath` pointing to your class
5. Set `"container": true` in the JSON if the datasource provides child elements
