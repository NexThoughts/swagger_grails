<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>Swagger Demo</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700|Source+Code+Pro:300,600|Titillium+Web:400,600,700"
          rel="stylesheet">
    <asset:stylesheet href="theme/swagger-ui.css"/>
</head>

<body>
<div class="content-view">

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="card demo-button">
                <div class="card-block">
                    <div id="swagger-ui"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<asset:javascript src="theme/swagger-ui-bundle.js"/>
<asset:javascript src="theme/swagger-ui-standalone-preset.js"/>
<script>
    window.onload = function () {
        // Build a system
        const ui = SwaggerUIBundle({
            url: "${apiDocsPath}",
            dom_id: '#swagger-ui',
//            deepLinking: true,
            defaultModelsExpandDepth: -1,
            presets: [
                SwaggerUIBundle.presets.apis,
                SwaggerUIStandalonePreset
            ],
            plugins: [],
            layout: "BaseLayout"
        });

        window.ui = ui
    }
</script>
</body>

</html>