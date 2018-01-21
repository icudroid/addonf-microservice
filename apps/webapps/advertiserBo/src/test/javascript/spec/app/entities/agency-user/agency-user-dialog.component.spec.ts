/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdvertiserBoTestModule } from '../../../test.module';
import { AgencyUserDialogComponent } from '../../../../../../main/webapp/app/entities/agency-user/agency-user-dialog.component';
import { AgencyUserService } from '../../../../../../main/webapp/app/entities/agency-user/agency-user.service';
import { AgencyUser } from '../../../../../../main/webapp/app/entities/agency-user/agency-user.model';
import { AgencyService } from '../../../../../../main/webapp/app/entities/agency';

describe('Component Tests', () => {

    describe('AgencyUser Management Dialog Component', () => {
        let comp: AgencyUserDialogComponent;
        let fixture: ComponentFixture<AgencyUserDialogComponent>;
        let service: AgencyUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [AgencyUserDialogComponent],
                providers: [
                    AgencyService,
                    AgencyUserService
                ]
            })
            .overrideTemplate(AgencyUserDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgencyUserDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgencyUserService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AgencyUser(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.agencyUser = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'agencyUserListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AgencyUser();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.agencyUser = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'agencyUserListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
