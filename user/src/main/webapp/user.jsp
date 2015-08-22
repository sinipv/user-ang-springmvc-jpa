<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="pt-BR" id="ng-app1" ng-app="">
<head>
<title><spring:message code="project.title" /></title>
<link href="<c:url value='resources/css/bootstrap.min.css'  />"
	rel="stylesheet" />
<link
	href="<c:url value='resources/css/bootstrap-responsive.min.css'  />"
	rel="stylesheet" />
<link href="<c:url value='resources/css/project_style.css'  />"
	rel="stylesheet" />
<script src="<c:url value='resources/js/jquery-1.9.1.min.js' />"></script>
<script src="<c:url value='resources/js/angular.min.js' />"></script>
</head>
<body>
	<div class="container">
		<div class="row-fluid" ng-controller="userController">
			<div id="addUsersModal">
				<div class="modal-body">
				<form name="newUserForm" novalidate>
					<div class="pull-left">
						<div>
							<div class="input-append">
								<label>* <spring:message code="users.name" />:
								</label>
							</div>
							<div class="input-append">
								<input type="text" required autofocus ng-model="user.name"
									name="name"
									placeholder="<spring:message code='user'/>&nbsp;<spring:message code='users.name'/>" />
							</div>
							<div class="input-append">
								<label>
									<span class="alert alert-error"
										  ng-show="displayValidationError && newUserForm.name.$error.required">
											<spring:message code="required"/>
									</span>
								</label>
							</div>
						</div>
						<div>
							<div class="input-append">
								<label>* <spring:message code="users.description" />:
								</label>
							</div>
							<div class="input-append">
								<input type="text" required ng-model="user.description"
									name="description"
									placeholder="<spring:message code='sample.description'/> " />
							</div>
							<div class="input-append">
							   <label>
										<span class="alert alert-error"
											  ng-show="displayValidationError && newUserForm.description.$error.required">
											<spring:message code="required"/>
										</span>
								</label>
								
							</div>
						</div>
						<div>
							<div class="input-append">
								<label>* <spring:message code="users.category" />:
								</label>
							</div>
							<div class="input-append">
								<input type="text" required ng-model="user.category"
									name="category"
									placeholder="<spring:message code='sample.category'/> " />
							</div>
							<div class="input-append">
								<label>
										<span class="alert alert-error"
											  ng-show="displayValidationError && newUserForm.category.$error.required">
											<spring:message code="required"/>
										</span>
								</label>
								
							</div>
						</div>
						<input type="submit" 
							ng-click="createUser(newUserForm);"
							value='<spring:message code="create"/>' />
					</div>
				</form>
			</div>
		
		</div>

		<h4>
			<div ng-class="{'': state == 'list', 'none': state != 'list'}">
				<p class="text-center">
					<spring:message code="message.total.records.found"/>:&nbsp;{{page.totalUsers}}
				</p>
			</div>
		</h4>

        <div id="gridContainer" ng-class="{'': state == 'list', 'none': state != 'list'}">
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th scope="col"><spring:message code="users.name"/></th>
                    <th scope="col"><spring:message code="users.description"/></th>
                    <th scope="col"><spring:message code="users.category"/></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="abc in page.source">
                    <td class="tdContactsCentered">{{abc.name}}</td>
                    <td class="tdContactsCentered">{{abc.description}}</td>
                    <td class="tdContactsCentered">{{abc.category}}</td>
                    <td class="width15">
                        <div class="text-center">
                            <input type="hidden" value="{{abc.id}}"/>
                            <a href="#updateContactsModal"
                               ng-click="selectedContact(contact);"
                               role="button"
                               title="<spring:message code="update"/>&nbsp;<spring:message code="contact"/>"
                               class="btn btn-inverse" data-toggle="modal">
                                <i class="icon-pencil"></i>
                            </a>
                            <a href="#deleteContactsModal"
                               ng-click="selectedContact(contact);"
                               role="button"
                               title="<spring:message code="delete"/>&nbsp;<spring:message code="contact"/>"
                               class="btn btn-inverse" data-toggle="modal">
                                <i class="icon-minus"></i>
                            </a>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="text-center">
                <button href="#" class="btn btn-inverse"
                        ng-class="{'btn-inverse': page.currentPage != 0, 'disabled': page.currentPage == 0}"
                        ng-disabled="page.currentPage == 0" ng-click="changePage(0)"
                        title='<spring:message code="pagination.first"/>'
                        >
                    <spring:message code="pagination.first"/>
                </button>
                <button href="#"
                        class="btn btn-inverse"
                        ng-class="{'btn-inverse': page.currentPage != 0, 'disabled': page.currentPage == 0}"
                        ng-disabled="page.currentPage == 0" class="btn btn-inverse"
                        ng-click="changePage(page.currentPage - 1)"
                        title='<spring:message code="pagination.back"/>'
                        >&lt;</button>
                <span>{{page.currentPage + 1}} <spring:message code="pagination.of"/> {{page.pagesCount}}</span>
                <button href="#"
                        class="btn btn-inverse"
                        ng-class="{'btn-inverse': page.pagesCount - 1 != page.currentPage, 'disabled': page.pagesCount - 1 == page.currentPage}"
                        ng-click="changePage(page.currentPage + 1)"
                        ng-disabled="page.pagesCount - 1 == page.currentPage"
                        title='<spring:message code="pagination.next"/>'
                        >&gt;</button>
                <button href="#"
                        class="btn btn-inverse"
                        ng-class="{'btn-inverse': page.pagesCount - 1 != page.currentPage, 'disabled': page.pagesCount - 1 == page.currentPage}"
                        ng-disabled="page.pagesCount - 1 == page.currentPage"
                        ng-click="changePage(page.pagesCount - 1)"
                        title='<spring:message code="pagination.last"/>'
                        >
                    <spring:message code="pagination.last"/>
                </button>
            </div>
        </div>
        
			<span class="alert alert-error dialogErrorMessage"
				  ng-show="errorOnSubmit">
				<spring:message code="request.error"/>
			</span>
		</div>
	</div>
	<script src="<c:url value="resources/js/pages/user.js" />"></script>
	
</body>
</html>

