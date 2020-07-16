# IT Agile Logic Core
Generic backend and frontend data structures for business logic

# Introduction

IT Agile Logic Core brings utilities for business applications.

# Example

The example below shows how to use MutableTreeNode to construct tree nodes and how to add children to each node.

```
    public AppResponse save(ExampleDTO dto) {
        AppResponseBuilder resp = new AppResponseBuilder();
        ...
        if (some validation) {
            resp.addError("{0} is not valid", dto.getValue())
        }
        ...
        return resp.build();
    }

```

This example generates an AppResponse:

```
{
    "ok": false,
    "messages": [
        { "type": "Error", "message": "value is not valid" }
    ]
}
```

# Development
## Maven
This project uses [Apache Maven](http://maven.apache.org/) as a build tool.  The convention for version numbers is major.minor.patch as stated by [SemVer 2.0](http://semver.org/). Under development code is marked with SNAPSHOT following maven standard.

## Git branching
Developed code adheres to the set of branching rules defined by [OneFlow - a Git branching model and workflow](http://endoflineblog.com/oneflow-a-git-branching-model-and-workflow)

