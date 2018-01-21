import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AdComponent } from './ad.component';
import { AdDetailComponent } from './ad-detail.component';
import { AdPopupComponent } from './ad-dialog.component';
import { AdDeletePopupComponent } from './ad-delete-dialog.component';

@Injectable()
export class AdResolvePagingParams implements Resolve<any> {

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

export const adRoute: Routes = [
    {
        path: 'ad',
        component: AdComponent,
        resolve: {
            'pagingParams': AdResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.ad.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ad/:id',
        component: AdDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.ad.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adPopupRoute: Routes = [
    {
        path: 'ad-new',
        component: AdPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.ad.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ad/:id/edit',
        component: AdPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.ad.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ad/:id/delete',
        component: AdDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.ad.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
