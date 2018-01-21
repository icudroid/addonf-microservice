import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AgencyUserComponent } from './agency-user.component';
import { AgencyUserDetailComponent } from './agency-user-detail.component';
import { AgencyUserPopupComponent } from './agency-user-dialog.component';
import { AgencyUserDeletePopupComponent } from './agency-user-delete-dialog.component';

@Injectable()
export class AgencyUserResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const agencyUserRoute: Routes = [
    {
        path: 'agency-user',
        component: AgencyUserComponent,
        resolve: {
            'pagingParams': AgencyUserResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agencyUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agency-user/:id',
        component: AgencyUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agencyUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agencyUserPopupRoute: Routes = [
    {
        path: 'agency-user-new',
        component: AgencyUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agencyUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agency-user/:id/edit',
        component: AgencyUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agencyUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agency-user/:id/delete',
        component: AgencyUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agencyUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
