/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdvertiserBoTestModule } from '../../../test.module';
import { MediaUserDetailComponent } from '../../../../../../main/webapp/app/entities/media-user/media-user-detail.component';
import { MediaUserService } from '../../../../../../main/webapp/app/entities/media-user/media-user.service';
import { MediaUser } from '../../../../../../main/webapp/app/entities/media-user/media-user.model';

describe('Component Tests', () => {

    describe('MediaUser Management Detail Component', () => {
        let comp: MediaUserDetailComponent;
        let fixture: ComponentFixture<MediaUserDetailComponent>;
        let service: MediaUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [MediaUserDetailComponent],
                providers: [
                    MediaUserService
                ]
            })
            .overrideTemplate(MediaUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MediaUser(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mediaUser).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
