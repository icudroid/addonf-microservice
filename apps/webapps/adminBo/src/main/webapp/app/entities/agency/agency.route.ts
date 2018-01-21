import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AgencyComponent } from './agency.component';
import { AgencyDetailComponent } from './agency-detail.component';
import { AgencyPopupComponent } from './agency-dialog.component';
import { AgencyDeletePopupComponent } from './agency-delete-dialog.component';

@Injectable()
export class AgencyResolvePagingParams implements Resolve<any> {

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

export const agencyRoute: Routes = [
    {
        path: 'agency',
        component: AgencyComponent,
        resolve: {
            'pagingParams': AgencyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agency/:id',
        component: AgencyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agencyPopupRoute: Routes = [
    {
        path: 'agency-new',
        component: AgencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agency/:id/edit',
        component: AgencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agency/:id/delete',
        component: AgencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
