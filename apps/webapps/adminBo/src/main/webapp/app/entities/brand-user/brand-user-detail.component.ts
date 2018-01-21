import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BrandUser } from './brand-user.model';
import { BrandUserService } from './brand-user.service';

@Component({
    selector: 'jhi-brand-user-detail',
    templateUrl: './brand-user-detail.component.html'
})
export class BrandUserDetailComponent implements OnInit, OnDestroy {

    brandUser: BrandUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private brandUserService: BrandUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBrandUsers();
    }

    load(id) {
        this.brandUserService.find(id).subscribe((brandUser) => {
            this.brandUser = brandUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBrandUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'brandUserListModification',
            (response) => this.load(this.brandUser.id)
        );
    }
}
