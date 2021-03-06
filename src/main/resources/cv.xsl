<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xls="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" version="5.0"
                doctype-system="about:legacy-compat"
                encoding="UTF-8"
                omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <!-- Latest compiled and minified CSS -->
                <xsl:text disable-output-escaping='yes'>
                    &lt;link href="static/js/bootstrap/css/bootstrap.min.css" rel="stylesheet"&gt;
                </xsl:text>
                <!-- Optional theme -->
                <xsl:text disable-output-escaping='yes'>
                    &lt;link href="static/js/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"&gt;
                </xsl:text>

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

                <title>Резюме</title>
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
                                <xsl:text disable-output-escaping='yes'>&lt;img id="imageresource" src="static/pic/photo.jpeg" class="img-thumbnail" width="370" height="500"</xsl:text>
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

                                <xsl:param name="gender">
                                    <xsl:value-of select="/contents/personal/@gender"/>
                                </xsl:param>

                                <xsl:param name="birthdate">
                                    <xsl:value-of select="/contents/personal/calc/birthdate/text()"/>
                                </xsl:param>

                                <xsl:param name="age">
                                    <xsl:value-of select="/contents/personal/calc/age/text()"/>
                                </xsl:param>

                                <xsl:value-of select="concat($gender, ', ', $birthdate)"/>

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

                            <xsl:if test="//contacts">
                                <div class="container" style="padding-left: 0px">
                                    <xsl:apply-templates select="//contacts/contact"/>
                                </div>
                            </xsl:if>

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
                    <p>Занятость:
                        <xsl:value-of select="/contents/position/workload/text()"/>
                    </p>
                    <p>График работы:
                        <xsl:value-of select="/contents/position/schedule/text()"/>
                    </p>
                    <div>
                        <xsl:param name="data-start">
                            <xsl:value-of select="/contents/experiences/@start"/>
                        </xsl:param>
                        <h4 id="experiences" data-start="{$data-start}">Опыт работы</h4>
                    </div>

                    <xsl:apply-templates select="/contents/experiences/experience"/>

                    <div class="row" style="shape-margin: 15px">
                        <div>
                            <h4>Ключевые навыки</h4>
                        </div>
                        <xsl:apply-templates select="/contents/skills/row"/>
                    </div>

                    <div class="row" style="shape-margin: 15px">
                        <div>
                            <h4>Обо мне</h4>
                        </div>
                        <div>
                            <h5>Личные качества:</h5>
                        </div>
                        <xsl:copy-of select="/contents/qualitys/node()"/>
                    </div>

                    <div class="row" style="shape-margin: 15px">
                        <div>
                            <h4>Высшее образование</h4>
                        </div>
                        <xsl:apply-templates select="/contents/educations/education"/>
                    </div>

                    <div class="row" style="shape-margin: 15px">
                        <div>
                            <h4>Знание языков</h4>
                        </div>
                        <ul>
                            <xsl:apply-templates select="/contents/languages/language"/>
                        </ul>
                    </div>

                    <div class="row" style="shape-margin: 15px">
                        <div>
                            <h4>Повышение квалификации, курсы</h4>
                        </div>
                        <xsl:apply-templates select="/contents/courses/course"/>
                    </div>

                    <div class="row" style="line-height: 10.0px">
                        <div>
                            <h4>Гражданство, время в пути до работы</h4>
                        </div>
                        <p>Гражданство:
                            <xsl:value-of select="/contents/additional/citizenship/text()"/>
                        </p>
                        <p>Желательное время в пути до работы:
                            <xsl:value-of select="/contents/additional/timetowork/text()"/>
                        </p>
                    </div>

                </div>

                <!-- Latest compiled and minified JavaScript -->
                <script src="static/js/jquery/jquery.js"></script>
                <!-- Bootstrap core JavaScript-->
                <script src="static/js/bootstrap/bootstrap.min.js"></script>
                <!-- Moment.js-->
                <script src="static/js/moment/moment.js"></script>
                <script src="static/js/moment/locale/ru.js"></script>

                <script src="static/js/main.js"></script>

                <script>
                    $("#pop").on("click", function () {
                    $('#imagepreview').attr('src', $('#imageresource').attr('src'));
                    $('#myModal').modal('show');
                    });
                </script>
            </body>
        </html>
    </xsl:template>

    <xls:template match="contact">
        <div class="row">
            <div class="col-md-1">
                <xsl:value-of select="@name"/>
            </div>
            <div class="col-md-5">
                <xsl:choose>
                    <xsl:when test="@href">
                        <xsl:param name="contact-href">
                            <xsl:value-of select="@href"/>
                        </xsl:param>
                        <xsl:param name="contact-target">
                            <xsl:value-of select="@target"/>
                        </xsl:param>
                        <a href="{$contact-href}" target="{$contact-target}"><xsl:value-of select="text()"/></a>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="text()"/>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:if test="@default='true'"> - предпочитаемый способ связи</xsl:if>
            </div>
        </div>
    </xls:template>

    <xsl:template match="experience">
        <div class="row" style="margin-top: 15px">
            <div class="col-md-3">
                <xsl:param name="start">
                    <xsl:value-of select="@start"/>
                </xsl:param>
                <xsl:param name="end">
                    <xsl:value-of select="@end"/>
                </xsl:param>
                <div class="experiencesrow" data-start="{$start}" data-end="{$end}">
                    <!--<xsl:value-of select="@seniority"/>-->
                    <!--<div>-->
                    <!--<xsl:value-of select="@duration"/>-->
                    <!--</div>-->
                </div>
            </div>
            <div class="col-md-5">
                <div class="bloko-column bloko-column_s-6 bloko-column_m-7 bloko-column_l-10">
                    <div>
                        <h4>
                            <xsl:value-of select="@title"/>
                        </h4>
                        <p>
                            <xsl:param name="url">
                                <xsl:value-of select="@url"/>
                            </xsl:param>
                            <!--<span><a href="http://www.reksoft.com/ru/">http://www.reksoft.com/ru/</a></span>-->
                            <span>
                                <a href="{$url}" target="_blank">
                                    <xsl:value-of select="@url"/>
                                </a>
                            </span>
                        </p>
                    </div>
                    <div>
                        <xsl:value-of select="position/@name"/>
                    </div>
                    <div>
                        <xsl:copy-of select="description/node()"/>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
    <xsl:template match="row">
        <h3>
            <xsl:apply-templates select="skill"/>
        </h3>
    </xsl:template>
    <xsl:template match="skill">
        <div class="label label-default">
            <xsl:value-of select="text()"/>
        </div>
    </xsl:template>
    <xsl:template match="education">
        <div class="row" style="margin-top: 15px">
            <div class="col-md-3">
                <span>
                    <xsl:value-of select="@graduated"/>
                </span>
            </div>
            <div class="col-md-5">
                <h5>
                    <b>
                        <xsl:value-of select="@name"/>
                    </b>
                </h5>
                <span>
                    <xsl:value-of select="@specialty"/>
                </span>
            </div>
        </div>
    </xsl:template>
    <xsl:template match="language">
        <li>
            <xsl:value-of select="text()"/>
        </li>
    </xsl:template>
    <xsl:template match="course">
        <div class="row" style="margin-top: 15px">
            <div class="col-md-3">
                <span>
                    <xsl:value-of select="@graduated"/>
                </span>
            </div>
            <div class="col-md-5">
                <h5>
                    <b>
                        <xsl:value-of select="@name"/>
                    </b>
                </h5>
                <span>
                    <xsl:value-of select="@desc"/>
                </span>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>