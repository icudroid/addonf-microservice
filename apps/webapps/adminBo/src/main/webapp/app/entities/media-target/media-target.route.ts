import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MediaTargetComponent } from './media-target.component';
import { MediaTargetDetailComponent } from './media-target-detail.component';
import { MediaTargetPopupComponent } from './media-target-dialog.component';
import { MediaTargetDeletePopupComponent } from './media-target-delete-dialog.component';

@Injectable()
export class MediaTargetResolvePagingParams implements Resolve<any> {

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

export const mediaTargetRoute: Routes = [
    {
        path: 'media-target',
        component: MediaTargetComponent,
        resolve: {
            'pagingParams': MediaTargetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaTarget.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'media-target/:id',
        component: MediaTargetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaTarget.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mediaTargetPopupRoute: Routes = [
    {
        path: 'media-target-new',
        component: MediaTargetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaTarget.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'media-target/:id/edit',
        component: MediaTargetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaTarget.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'media-target/:id/delete',
        component: MediaTargetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaTarget.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
