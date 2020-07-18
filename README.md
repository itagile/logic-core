# IT Agile Logic Core
Generic backend and frontend data structures for business logic

# Introduction

IT Agile Logic Core brings utilities for business applications.

# Example

The example below shows how to use AppResponseBuilder to construct an AppResponse object with messages for API clients.

The response object can have useful messages for showing to the user.

```java
private boolean isValid(ExampleDTO dto, ResponseBuilder resp) {
    if (some validation) {
        resp.addError("{0} is not valid", dto.getValue());
    }
    if (some validation) {
        resp.addWarn("Warning message: {0}", dto.getValue());
    }
    return resp.isOk();
}

public AppResponse save(ExampleDTO dto) {
    var resp = new ResponseBuilder.of();
    if (isValid(dto, resp)) {
        // save logic
        resp.addInfo("Optional success message");
    }
    return resp.build();
}

```

This example generates an AppResponse like this when errors are present:

```json
{
    "ok": false,
    "messages": [
        { "type": "ERROR", "message": "value is not valid" },
        { "type": "WARN", "message": "Warning message: value" }
    ]
}
```

or an AppResponse like this when no errors were found:


```json
{
    "ok": true,
    "messages": [
        { "type": "INFO", "message": "Optional success message" }
    ]
}
```

The AppResponse is a simple JSON serializable DTO for use in REST API responses.

If more properties in response are needed is possible to inherit from AppResponse and use generic AppResponseClassBuilder to build this special type.

The example below shows a custom response adding an id to the response:

```java
public class MyCustomResponse extends AppResponse {
    private Long id;
    ...
}

...

private boolean isValid(ExampleDTO dto, ResponseBuilder resp) {
    if (some validation) {
        resp.addError("{0} is not valid", dto.getValue());
    }
    if (some validation) {
        resp.addWarn("Warning message: {0}", dto.getValue());
    }
    return resp.isOk();
}


public AppResponse save(ExampleDTO dto) {
    var resp = ResponseBuilder.of(MyCustomResponse::new);
    if (isValid(dto, resp)) {
        // save logic
        resp.build().setId(id);
        resp.addInfo("Optional success message");
    }
    ...
    return resp.build();
}

```

Subsequent calls to build method will return the same instance. The aforementioned is to ease setting other properties of the response.

MyCustomResponse::new is the recommended way to initialize the custom response builder, nevertheless, passing a class is supported too:

```java
    var resp = ResponseBuilder.of(MyCustomResponse.class);
```


# Development
## Maven
This project uses [Apache Maven](http://maven.apache.org/) as a build tool.  The convention for version numbers is major.minor.patch as stated by [SemVer 2.0](http://semver.org/). Under development code is marked with SNAPSHOT following maven standard.

## Git branching
Developed code adheres to the set of branching rules defined by [OneFlow - a Git branching model and workflow](http://endoflineblog.com/oneflow-a-git-branching-model-and-workflow)

