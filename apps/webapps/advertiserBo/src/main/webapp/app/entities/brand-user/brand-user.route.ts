import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BrandUserComponent } from './brand-user.component';
import { BrandUserDetailComponent } from './brand-user-detail.component';
import { BrandUserPopupComponent } from './brand-user-dialog.component';
import { BrandUserDeletePopupComponent } from './brand-user-delete-dialog.component';

@Injectable()
export class BrandUserResolvePagingParams implements Resolve<any> {

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

export const brandUserRoute: Routes = [
    {
        path: 'brand-user',
        component: BrandUserComponent,
        resolve: {
            'pagingParams': BrandUserResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.brandUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'brand-user/:id',
        component: BrandUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.brandUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const brandUserPopupRoute: Routes = [
    {
        path: 'brand-user-new',
        component: BrandUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.brandUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-user/:id/edit',
        component: BrandUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.brandUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-user/:id/delete',
        component: BrandUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.brandUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
