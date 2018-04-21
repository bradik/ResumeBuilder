<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" version="5.0"
                doctype-system="about:legacy-compat"
                encoding="UTF-8"
                omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Резюме</title>
                <!--https://stackoverflow.com/questions/10184694/how-to-create-hyperlink-using-xslt-->
                <xsl:text disable-output-escaping='yes'>&lt;link rel="stylesheet" href="dist/css/bootstrap.min.css" rel="stylesheet"&gt;</xsl:text>
                <style>
                    .box1 {
                    background: rgba(203, 209, 211, .3);
                    color: #666;
                    padding: 5px;
                    margin: 3px;
                    }
                </style>
            </head>
            <body>
                <div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <div class="modal-body">
                                <img id="imagepreview" class="img-thumbnail"/>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="container">
                    <div class="row" style="margin-top: 15px">
                        <div class="col-md-2">
                            <a href="#" id="pop">
                                <xsl:text disable-output-escaping='yes'>&lt;img id="imageresource" src="dist/pic/pic.jpeg" class="img-thumbnail" width="370" height="500"
                                     alt="Яковлев Родион"&gt;</xsl:text>
                            </a>
                        </div>
                        <div class="col-md-6">
                            <h3 id="personalName">
                                <xsl:copy-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='Name']/text()"/>
                            </h3>
                            <div>Мужчина, 41 год, родился 4 декабря 1975</div>
                            <div>
                                <xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='AddressLocality']/@address"/>,
                                <span style="color: red">
                                    <xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='AddressLocality']/@metro"/>
                                </span>
                                <span>
                                    <xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='AddressLocality']/@info"/>
                                </span>
                            </div>
                            <p/>
                            <div>
                                <xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='Telephone']"/>
                            </div>
                            <div>
                                <a href="mailto:bradnm@ya.ru" itemprop="email">
                                    <xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='Email']/text()"/>
                                </a>
                                - предпочитаемый способ связи
                            </div>
                        </div>
                    </div>
                    <hr/>


                </div>

                <!-- Bootstrap core JavaScript
                ================================================== -->
                <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
                        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
                        crossorigin="anonymous"></script>
                <script src="dist/js/bootstrap.min.js"></script>

                <script>
                    $("#pop").on("click", function () {
                    $('#imagepreview').attr('src', $('#imageresource').attr('src')); // here asign the image to the modal when the user click the enlarge link
                    $('#myModal').modal('show'); // imagemodal is the id attribute assigned to the bootstrap modal, then i use the show function
                    });
                </script>



                <!--<div class="resume-header-wrapper">-->
                    <!--<div class="resume-photo"></div>-->
                    <!--<div class="resume-header">-->
                        <!--<div class="resume-header-name">-->
                            <!--<h1 class="header"><xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='Name']/text()"/></h1>-->
                        <!--</div>-->
                        <!--<div class="resume-header-addressLocality">-->
                            <!--<xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='AddressLocality']/@address"/>,-->
                            <!--<xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='AddressLocality']/@metro"/>-->
                        <!--</div>-->
                        <!--<div class="resume-header-telephone">-->
                            <!--<xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='Telephone']/text()"/>-->
                        <!--</div>-->
                        <!--<div class="resume-header-email">-->
                            <!--<xsl:value-of select="/*[name()='Contents']/*[name()='Personal']/*[name()='Email']/text()"/>-->
                        <!--</div>-->
                    <!--</div>-->
                <!--</div>-->
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>