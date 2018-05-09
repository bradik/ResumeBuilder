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
                    .label-large {
                    vertical-align: super;
                    font-size: large;
                    }
                </style>
            </head>
            <body>
                <div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
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
                                <xsl:text disable-output-escaping='yes'>&lt;img id="imageresource" src="dist/pic/pic.jpeg" class="img-thumbnail" width="370" height="500"</xsl:text>
                                <xsl:text>alt="</xsl:text>
                                <xsl:value-of select="/contents/personal/name/text()"/>
                                <xsl:text disable-output-escaping='yes'>"&gt;</xsl:text>
                            </a>
                        </div>
                        <div class="col-md-6">
                            <h3 id="personalName">
                                <xsl:value-of select="/contents/personal/name/text()"/>
                            </h3>
                            <div>
                                <!--Мужчина, 41 год, родился 4 декабря 1975-->
                                <xsl:value-of select="/contents/personal/@gender"/>
                                <xsl:text>, </xsl:text>
                                <xsl:value-of select="/contents/personal/calc/birthdate/text()"/>
                                <xsl:text>, </xsl:text>
                                <xsl:value-of select="/contents/personal/calc/age/text()"/>
                            </div>
                            <div>
                                <xsl:value-of select="/contents/personal/addresslocality/@address"/>,
                                <span style="color: red">
                                    <xsl:value-of select="/contents/personal/addresslocality/@metro"/>
                                </span>
                                <span>
                                    <xsl:value-of select="contents/persona/addresslocality/@info"/>
                                </span>
                            </div>
                            <p/>
                            <div>
                                <xsl:value-of select="contents/personal/telephone"/>
                            </div>
                            <!--<div><a href="mailto:bradnm@ya.ru" itemprop="email">bradnm@ya.ru</a>- предпочитаемый способ связи</div>-->
                            <div>
                                <xsl:text disable-output-escaping='yes'>&lt;a </xsl:text>
                                <xsl:text>href="mailto:bradnm@ya.ru" itemprop="email"</xsl:text>
                                <xsl:text disable-output-escaping='yes'>&gt;</xsl:text>
                                <xsl:value-of select="contents/personal/email/text()"/>
                                <xsl:text disable-output-escaping='yes'>&gt;&lt;/a&gt;</xsl:text>
                                - предпочитаемый способ связи
                            </div>
                        </div>
                    </div>
                    <hr/>
                </div>

                <div class="container">
                    <h4>
                        <xsl:value-of select="/contents/position/@name"/>
                    </h4>
                    <div>
                        <span>
                            <xsl:value-of select="/contents/position/category/text()"/>
                        </span>
                        <ul>
                            <li>
                                <xsl:value-of select="/contents/position/specialization/text()"/>
                            </li>
                        </ul>
                    </div>
                    <p>Занятость: <xsl:value-of select="/contents/position/workload/text()"/></p>
                    <p>График работы: <xsl:value-of select="/contents/position/schedule/text()"/></p>
                    <div>
                        <h4>Опыт работы <xsl:value-of select="/contents/experiences/@seniority"/></h4>
                    </div>

                    <xsl:apply-templates select="/contents/experiences/experience"/>

                    <div class="row" style="shape-margin: 15px">
                        <div><h4>Ключевые навыки</h4></div>
                        <xsl:apply-templates select="/contents/skills/skill"/>
                    </div>

                    <div class="row" style="shape-margin: 15px">
                        <div><h4>Обо мне</h4></div>
                        <div><h5>Личные качества:</h5></div>
                        <ul>
                            <xsl:apply-templates select="/contents/qualitys/quality"/>
                        </ul>
                    </div>

                    <div class="row" style="shape-margin: 15px">
                        <div><h4>Высшее образование</h4></div>
                        <xsl:apply-templates select="/contents/educations/education"/>
                    </div>

                    <div class="row" style="shape-margin: 15px">
                        <div><h4>Знание языков</h4></div>
                        <ul>
                            <xsl:apply-templates select="/contents/languages/language"/>
                        </ul>
                    </div>

                    <div class="row" style="shape-margin: 15px">
                        <div><h4>Повышение квалификации, курсы</h4></div>
                        <xsl:apply-templates select="/contents/courses/course"/>
                    </div>

                    <div class="row" style="line-height: 10.0px">
                        <div><h4>Гражданство, время в пути до работы</h4></div>
                        <p>Гражданство: <xsl:value-of select="/contents/additional/citizenship/text()"/></p>
                        <p>Желательное время в пути до работы:  <xsl:value-of select="/contents/additional/timetowork/text()"/></p>
                    </div>

                </div>
                <!-- Bootstrap core JavaScript
                ================================================== -->
                <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
                        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
                        crossorigin="anonymous"></script>
                <script src="dist/js/bootstrap.min.js"></script>

                <script>
                    $("#pop").on("click", function () {
                    $('#imagepreview').attr('src', $('#imageresource').attr('src'));
                    $('#myModal').modal('show');
                    });
                </script>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="experience">
        <div class="row" style="margin-top: 15px">
            <div class="col-md-3">
                <div>
                    <xsl:value-of select="@seniority"/>
                    <div>
                        <xsl:value-of select="@duration"/>
                    </div>
                </div>
            </div>
            <div class="col-md-5">
                <div class="bloko-column bloko-column_s-6 bloko-column_m-7 bloko-column_l-10">
                    <div>
                        <h4>
                            <xsl:value-of select="@title"/>
                        </h4>
                        <p>
                            <span>
                                <xsl:text disable-output-escaping='yes'>&lt;a href="</xsl:text>
                                <xsl:value-of select="@url"/>
                                <xsl:text disable-output-escaping='yes'>"&gt;</xsl:text>
                                <xsl:value-of select="@url"/>
                                <xsl:text disable-output-escaping='yes'>&lt;/a&gt;</xsl:text>
                            </span>
                        </p>
                    </div>
                    <div>
                        <xsl:value-of select="position/@name"/>
                    </div>
                    <div>
                        <xsl:copy-of select="description/div"/>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
    <xsl:template match="skill">
        <span class="badge badge-primary">
            <xsl:value-of select="text()"> </xsl:value-of>
        </span>
    </xsl:template>
    <xsl:template match="quality">
        <li><xsl:value-of select="text()"/></li>
    </xsl:template>
    <xsl:template match="education">
        <div class="row" style="margin-top: 15px">
            <div class="col-md-3">
                <span><xsl:value-of select="@graduated"/></span>
            </div>
            <div class="col-md-5">
                <h5><b><xsl:value-of select="@name"/></b></h5>
                <span><xsl:value-of select="@specialty"/></span>
            </div>
        </div>
    </xsl:template>
    <xsl:template match="language">
        <li><xsl:value-of select="text()"/></li>
    </xsl:template>
    <xsl:template match="course">
        <div class="row" style="margin-top: 15px">
            <div class="col-md-3">
                <span><xsl:value-of select="@graduated"/></span>
            </div>
            <div class="col-md-5">
                <h5><b><xsl:value-of select="@name"/></b></h5>
                <span><xsl:value-of select="@desc"/></span>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>