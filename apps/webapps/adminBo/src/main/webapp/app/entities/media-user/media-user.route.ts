import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MediaUserComponent } from './media-user.component';
import { MediaUserDetailComponent } from './media-user-detail.component';
import { MediaUserPopupComponent } from './media-user-dialog.component';
import { MediaUserDeletePopupComponent } from './media-user-delete-dialog.component';

@Injectable()
export class MediaUserResolvePagingParams implements Resolve<any> {

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

export const mediaUserRoute: Routes = [
    {
        path: 'media-user',
        component: MediaUserComponent,
        resolve: {
            'pagingParams': MediaUserResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'media-user/:id',
        component: MediaUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mediaUserPopupRoute: Routes = [
    {
        path: 'media-user-new',
        component: MediaUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'media-user/:id/edit',
        component: MediaUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'media-user/:id/delete',
        component: MediaUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.mediaUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
