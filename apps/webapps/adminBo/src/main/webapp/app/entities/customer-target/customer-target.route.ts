import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CustomerTargetComponent } from './customer-target.component';
import { CustomerTargetDetailComponent } from './customer-target-detail.component';
import { CustomerTargetPopupComponent } from './customer-target-dialog.component';
import { CustomerTargetDeletePopupComponent } from './customer-target-delete-dialog.component';

@Injectable()
export class CustomerTargetResolvePagingParams implements Resolve<any> {

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

export const customerTargetRoute: Routes = [
    {
        path: 'customer-target',
        component: CustomerTargetComponent,
        resolve: {
            'pagingParams': CustomerTargetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.customerTarget.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'customer-target/:id',
        component: CustomerTargetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.customerTarget.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customerTargetPopupRoute: Routes = [
    {
        path: 'customer-target-new',
        component: CustomerTargetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.customerTarget.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-target/:id/edit',
        component: CustomerTargetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.customerTarget.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-target/:id/delete',
        component: CustomerTargetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.customerTarget.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
