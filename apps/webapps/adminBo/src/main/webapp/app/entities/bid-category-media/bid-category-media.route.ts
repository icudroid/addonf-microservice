import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BidCategoryMediaComponent } from './bid-category-media.component';
import { BidCategoryMediaDetailComponent } from './bid-category-media-detail.component';
import { BidCategoryMediaPopupComponent } from './bid-category-media-dialog.component';
import { BidCategoryMediaDeletePopupComponent } from './bid-category-media-delete-dialog.component';

@Injectable()
export class BidCategoryMediaResolvePagingParams implements Resolve<any> {

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

export const bidCategoryMediaRoute: Routes = [
    {
        path: 'bid-category-media',
        component: BidCategoryMediaComponent,
        resolve: {
            'pagingParams': BidCategoryMediaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.bidCategoryMedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bid-category-media/:id',
        component: BidCategoryMediaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.bidCategoryMedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bidCategoryMediaPopupRoute: Routes = [
    {
        path: 'bid-category-media-new',
        component: BidCategoryMediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.bidCategoryMedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bid-category-media/:id/edit',
        component: BidCategoryMediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.bidCategoryMedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bid-category-media/:id/delete',
        component: BidCategoryMediaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.bidCategoryMedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
