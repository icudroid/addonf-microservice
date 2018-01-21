/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { AdDialogComponent } from '../../../../../../main/webapp/app/entities/ad/ad-dialog.component';
import { AdService } from '../../../../../../main/webapp/app/entities/ad/ad.service';
import { Ad } from '../../../../../../main/webapp/app/entities/ad/ad.model';
import { AdCampaingService } from '../../../../../../main/webapp/app/entities/ad-campaing';

describe('Component Tests', () => {

    describe('Ad Management Dialog Component', () => {
        let comp: AdDialogComponent;
        let fixture: ComponentFixture<AdDialogComponent>;
        let service: AdService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [AdDialogComponent],
                providers: [
                    AdCampaingService,
                    AdService
                ]
            })
            .overrideTemplate(AdDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Ad(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.ad = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'adListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Ad();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.ad = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'adListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
