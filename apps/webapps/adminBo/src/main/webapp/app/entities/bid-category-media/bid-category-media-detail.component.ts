import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BidCategoryMedia } from './bid-category-media.model';
import { BidCategoryMediaService } from './bid-category-media.service';

@Component({
    selector: 'jhi-bid-category-media-detail',
    templateUrl: './bid-category-media-detail.component.html'
})
export class BidCategoryMediaDetailComponent implements OnInit, OnDestroy {

    bidCategoryMedia: BidCategoryMedia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bidCategoryMediaService: BidCategoryMediaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBidCategoryMedias();
    }

    load(id) {
        this.bidCategoryMediaService.find(id).subscribe((bidCategoryMedia) => {
            this.bidCategoryMedia = bidCategoryMedia;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBidCategoryMedias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bidCategoryMediaListModification',
            (response) => this.load(this.bidCategoryMedia.id)
        );
    }
}
