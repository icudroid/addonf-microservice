import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AdCampaingComponent } from './ad-campaing.component';
import { AdCampaingDetailComponent } from './ad-campaing-detail.component';
import { AdCampaingPopupComponent } from './ad-campaing-dialog.component';
import { AdCampaingDeletePopupComponent } from './ad-campaing-delete-dialog.component';

@Injectable()
export class AdCampaingResolvePagingParams implements Resolve<any> {

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

export const adCampaingRoute: Routes = [
    {
        path: 'ad-campaing',
        component: AdCampaingComponent,
        resolve: {
            'pagingParams': AdCampaingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adCampaing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ad-campaing/:id',
        component: AdCampaingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adCampaing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adCampaingPopupRoute: Routes = [
    {
        path: 'ad-campaing-new',
        component: AdCampaingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adCampaing.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ad-campaing/:id/edit',
        component: AdCampaingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adCampaing.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ad-campaing/:id/delete',
        component: AdCampaingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adCampaing.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
