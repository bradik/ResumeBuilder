<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
    <head>
        <!-- Latest compiled and minified CSS -->
        <link href="static/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="static/js/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">

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

        <title>${dataService.getResource("title")!}</title>
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
                    <a href="#" id="pop"><img id="imageresource" src="static/pic/photo.jpeg" class="img-thumbnail" width="370" height="500" alt="${data.personal.name}"></a>
                </div>
                <div class="col-md-6">
                    <h3 id="personalName">${data.personal.name}</h3>
                    <div>${dataService.getPersonInfo(data)}</div>
                    <div>
                        ${data.personal.addresslocality.address},
                        <span style="color: red">${data.personal.addresslocality.metro}</span>
                        <span>${data.personal.addresslocality.info}</span>
                    </div>
                    <p></p>
                    <div class="container" style="padding-left: 0px">
                        <#list data.personal.contacts as contact>
                        <div class="row">
                            <div class="col-md-1">${contact.name!}</div>
                            <#if contact.value??>
                                <div class="col-md-5">
                                    <#if contact.href??>
                                        <a href="${contact.href}" target="${contact.target!}">${contact.value}</a>
                                        <#if contact.def>- ${dataService.getResource("default.contact")!}</#if>
                                    <#else>
                                        ${contact.value}
                                    </#if>
                                </div>
                            </#if>
                        </div>
                        </#list>
                    </div>
                </div>
            </div>
            <hr/>
        </div>

        <div class="container">
            <h4>${data.position.name!}</h4>
            <div>
                <span>${data.position.category!}</span>
                <ul>
                    <li>${data.position.specialization!}</li>
                </ul>
            </div>
            <p>${dataService.getResource("employment")!}:
                ${data.position.workload!}</p>
            <p>${dataService.getResource("schedule")!}:
                ${data.position.schedule!}</p>
            <div>
                <h4 id="experiences">${dataService.getWorkExperience(data)}</h4>
            </div>

            <#list data.experiences.experience as experience>
                <div class="row" style="margin-top: 15px">
                    <div class="col-md-3">
                        <div class="experiencesrow" data-start="2020-10" data-end="">
                            ${dataService.getSeniority(experience)!}
                            <div>${dataService.getDuration(experience)!}</div>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <div class="bloko-column bloko-column_s-6 bloko-column_m-7 bloko-column_l-10">
                            <div>
                                <h4>${experience.title!}</h4>
                                <p>
                                    <span><a href=${experience.url!} target="_blank">${experience.url!}</a></span>
                                </p>
                            </div>
                            <div>${experience.position.name!}</div>
                            <div>
                                <#noautoesc>
                                ${dataService.getFormattedDescription(experience.description)!}
                                </#noautoesc>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>

            <div class="row" style="shape-margin: 15px">
                <div>
                    <h4>${dataService.getResource("key.skills")!}</h4>
                </div>
                <#list data.skills as row>
                    <h3>
                    <#list row.skill as skill>
                        <div class="label label-default">${skill}</div>
                    </#list>
                    </h3>
                </#list>
            </div>
            <div class="row" style="shape-margin: 15px">
                <div>
                    <h4>${dataService.getResource("about.me")!}</h4>
                </div>
                <div>
                    <h5>${dataService.getResource("personal.qualities")!}:</h5>
                </div>
                <#noautoesc>
                    ${dataService.getFormattedDescription(data.qualitys!)!}
                </#noautoesc>
                <div>
                    <h5>${dataService.getResource("interests")!}:</h5>
                </div>
                <#noautoesc>
                    ${dataService.getFormattedDescription(data.interests!)!}
                </#noautoesc>
            </div>
            <div class="row" style="shape-margin: 15px">
                <div>
                    <h4>${dataService.getResource("higher.education")!}</h4>
                </div>
                <#list data.educations as education>
                <div class="row" style="margin-top: 15px">
                    <div class="col-md-3">
                        <span>${education.graduated}</span>
                    </div>
                    <div class="col-md-5">
                        <h5>
                            <b>${education.name}</b>
                        </h5>
                        <span>${education.specialty}</span>
                    </div>
                </div>
                </#list>
            </div>
            <div class="row" style="shape-margin: 15px">
                <div>
                    <h4>${dataService.getResource("languages")!}</h4>
                </div>
                <ul>
                <#list data.languages as language>
                    <li>${language}</li>
                </#list>
                </ul>
            </div>
            <div class="row" style="shape-margin: 15px">
                <div>
                    <h4>${dataService.getResource("courses")!}</h4>
                </div>
                <#list data.courses as course>
                <div class="row" style="margin-top: 15px">
                    <div class="col-md-3">
                        <span>${course.graduated}</span>
                    </div>
                    <div class="col-md-5">
                        <h5>
                            <b>${course.name}</b>
                        </h5>
                        <span>${course.desc}</span>
                    </div>
                </div>
                </#list>
            </div>
            <div class="row" style="line-height: 10.0px">
                <div>
                    <h4>${dataService.getResource("additional")!}</h4>
                </div>
                <p>${dataService.getResource("citizenship")!}:
                    ${data.additional.citizenship}</p>
                <p>${dataService.getResource("timetowork")!}:
                    ${data.additional.timetowork}</p>
            </div>
        </div>

        <!-- Latest compiled and minified JavaScript -->
        <script src="static/js/jquery/jquery.js"></script>
        <!-- Bootstrap core JavaScript-->
        <script src="static/js/bootstrap/bootstrap.min.js"></script>
        <script>
            $("#pop").on("click", function () {
                $('#imagepreview').attr('src', $('#imageresource').attr('src'));
                $('#myModal').modal('show');
            });
        </script>
    </body>
</html>