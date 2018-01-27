import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ProfileComponent } from './profile.component';
import { ProfileDetailComponent } from './profile-detail.component';
import { ProfilePopupComponent } from './profile-dialog.component';
import { ProfileDeletePopupComponent } from './profile-delete-dialog.component';

@Injectable()
export class ProfileResolvePagingParams implements Resolve<any> {

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

export const profileRoute: Routes = [
    {
        path: 'profile',
        component: ProfileComponent,
        resolve: {
            'pagingParams': ProfileResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.profile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'profile/:id',
        component: ProfileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.profile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profilePopupRoute: Routes = [
    {
        path: 'profile-new',
        component: ProfilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.profile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile/:id/edit',
        component: ProfilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.profile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile/:id/delete',
        component: ProfileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.profile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
