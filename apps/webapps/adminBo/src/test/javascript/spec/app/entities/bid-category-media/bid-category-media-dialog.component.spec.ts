/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { BidCategoryMediaDialogComponent } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media-dialog.component';
import { BidCategoryMediaService } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media.service';
import { BidCategoryMedia } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media.model';
import { AdService } from '../../../../../../main/webapp/app/entities/ad';
import { CategoryService } from '../../../../../../main/webapp/app/entities/category';
import { MediaService } from '../../../../../../main/webapp/app/entities/media';

describe('Component Tests', () => {

    describe('BidCategoryMedia Management Dialog Component', () => {
        let comp: BidCategoryMediaDialogComponent;
        let fixture: ComponentFixture<BidCategoryMediaDialogComponent>;
        let service: BidCategoryMediaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [BidCategoryMediaDialogComponent],
                providers: [
                    AdService,
                    CategoryService,
                    MediaService,
                    BidCategoryMediaService
                ]
            })
            .overrideTemplate(BidCategoryMediaDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BidCategoryMediaDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BidCategoryMediaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BidCategoryMedia(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.bidCategoryMedia = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bidCategoryMediaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BidCategoryMedia();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.bidCategoryMedia = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bidCategoryMediaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
