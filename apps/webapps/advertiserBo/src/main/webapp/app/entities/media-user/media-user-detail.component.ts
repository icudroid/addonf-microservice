import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MediaUser } from './media-user.model';
import { MediaUserService } from './media-user.service';

@Component({
    selector: 'jhi-media-user-detail',
    templateUrl: './media-user-detail.component.html'
})
export class MediaUserDetailComponent implements OnInit, OnDestroy {

    mediaUser: MediaUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mediaUserService: MediaUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMediaUsers();
    }

    load(id) {
        this.mediaUserService.find(id).subscribe((mediaUser) => {
            this.mediaUser = mediaUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMediaUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mediaUserListModification',
            (response) => this.load(this.mediaUser.id)
        );
    }
}
