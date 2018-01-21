import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MediaTarget } from './media-target.model';
import { MediaTargetService } from './media-target.service';

@Component({
    selector: 'jhi-media-target-detail',
    templateUrl: './media-target-detail.component.html'
})
export class MediaTargetDetailComponent implements OnInit, OnDestroy {

    mediaTarget: MediaTarget;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mediaTargetService: MediaTargetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMediaTargets();
    }

    load(id) {
        this.mediaTargetService.find(id).subscribe((mediaTarget) => {
            this.mediaTarget = mediaTarget;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMediaTargets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mediaTargetListModification',
            (response) => this.load(this.mediaTarget.id)
        );
    }
}
